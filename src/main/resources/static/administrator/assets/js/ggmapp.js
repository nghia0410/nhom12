options = {
    enableHighAccuracy: true,
    timeout: 5000,
    maximumAge: 0,
};
let map, infoWindow, currentCord;
//let placeId, name, rating, userRatingsTotal, formattedAddress, type, openingHours, websiteUrl;
async function getCurrentLocation(){
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                currentCord = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude,
                };
                currentMarker = new google.maps.Marker({
                    position: currentCord,
                    map: map,
                    title: 'Vị trí của bạn',
                    animation: google.maps.Animation.DROP,
                    icon: {
                        url: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png', // Thay đổi màu của đánh dấu thành màu xanh
                    }
                });
                return currentCord;
            },
            () => {
                handleLocationError(true, infoWindow, map.getCenter());
            },
            options
        );
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

async function initMap() {
    const UEFSchoolCord = { lat: 10.797436384668082, lng: 106.7035743219549 };
    currentCord = await getCurrentLocation();

    map = await new google.maps.Map(document.getElementById("googleMap"), {
        zoom: 16,
        center: UEFSchoolCord,
        zoomControl: false,
        scaleControl: true,
        mapTypeControl: true,
        mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
        }
    });

    new ClickEventHandler(map, origin);

    infoWindow = new google.maps.InfoWindow();
    //----------------------------------------

    //-----------------------------------------

    //------------------------------------------
    var searchBox = new google.maps.places.SearchBox(document.getElementById('searchInput'));
    //map.controls[google.maps.ControlPosition.TOP_CENTER].push(document.getElementById('search-input'));

    // Bias the SearchBox results towards current map's viewport.
    map.addListener("bounds_changed", () => {
        searchBox.setBounds(map.getBounds());
    });

    let markers = [];

    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener("places_changed", () => {
        const places = searchBox.getPlaces();

        if (places.length == 0) {
            return;
        }

        // Clear out the old markers.
        markers.forEach((marker) => {
            marker.setMap(null);
        });
        markers = [];

        // For each place, get the icon, name and location.
        const bounds = new google.maps.LatLngBounds();

        places.forEach((place) => {
            if (!place.geometry || !place.geometry.location) {
                console.log("Returned place contains no geometry");
                return;
            }

            const icon = {
                url: place.icon,
                size: new google.maps.Size(71, 71),
                origin: new google.maps.Point(0, 0),
                anchor: new google.maps.Point(17, 34),
                scaledSize: new google.maps.Size(25, 25),
            };

            // Create a marker for each place.
            markers.push(
                new google.maps.Marker({
                    map,
                    icon,
                    title: place.name,
                    position: place.geometry.location,
                }),
            );
            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }

            restarantInfoParse(place);

        });
        map.fitBounds(bounds);
    });
}
function extractOpeningHours(openingHoursData) {
    // Check if opening hours data is available
    if (!openingHoursData || !openingHoursData.periods) {
        return "N/A"; // Or any placeholder for missing data
    }

    // The Places API response format for opening hours can vary
    // Depending on the format, you might need to adjust the parsing logic

    const openingHoursArray = openingHoursData.periods.map(period => {
        try {
            const openingTime = period.open?.time;

            if (!openingTime) {
                return "N/A";
            }

            if (openingTime.length !== 4) {
                return "Invalid closing time format";
            }
            const hours = parseInt(openingTime.slice(0, 2));
            const minutes = parseInt(openingTime.slice(2));

            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                return "Invalid closing time format";
            }
            return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
        } catch (error) {
            console.error("Error parsing opening hours:", error);
            // Return a more informative error message
            return "Invalid opening hours format";
        }
    });

    // If there are multiple opening periods, return the first one
    return openingHoursArray[0] || "N/A";
}

function extractClosingHours(openingHoursData) {
    // Similar logic to extractOpeningHours
    if (!openingHoursData || !openingHoursData.periods) {
        return "N/A";
    }

    const closingHoursArray = openingHoursData.periods.map(period => {
        try {
            const closingTime = period.close?.time;

            if (!closingTime) {
                return "N/A";
            }

            if (closingTime.length !== 4) {
                return "Invalid closing time format";
            }
            const hours = parseInt(closingTime.slice(0, 2));
            const minutes = parseInt(closingTime.slice(2));

            if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
                return "Invalid closing time format";
            }
            return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;

        } catch (error) {
            console.error("Error parsing closing hours:", error);
            // Return a more informative error message
            return "Invalid closing hours format";
        }
    });
    return closingHoursArray[0] || "N/A";
}

function isIconMouseEvent(e) {
    return "placeId" in e;
}

class ClickEventHandler {
    origin;
    map;
    directionsService;
    directionsRenderer;
    placesService;
    infowindow;
    infowindowContent;

    constructor(map, origin) {
        this.origin = origin;
        this.map = map;
        // this.directionsService = new google.maps.DirectionsService();
        // this.directionsRenderer = new google.maps.DirectionsRenderer();
        // this.directionsRenderer.setMap(map);
        this.placesService = new google.maps.places.PlacesService(map);
        this.infowindow = new google.maps.InfoWindow();
        this.infowindowContent = document.getElementById("infowindow-content");
        this.infowindow.setContent(this.infowindowContent);
        // Listen for clicks on the map.
        this.map.addListener("click", this.handleClick.bind(this));
    }

    handleClick(event) {
        console.log("You clicked on: " + event.latLng);
        // If the event has a placeId, use it.
        if (isIconMouseEvent(event)) {
            console.log("You clicked on place:" + event.placeId);

            event.stop();
            if (event.placeId) {
                //this.calculateAndDisplayRoute(event.placeId);
                this.getPlaceInformation(event.placeId);
            }
        }
    }

    // calculateAndDisplayRoute(placeId) {
    //     const me = this;
    //
    //     this.directionsService
    //         .route({
    //             origin: this.origin,
    //             destination: {placeId: placeId},
    //             travelMode: google.maps.TravelMode.WALKING,
    //         })
    //         .then((response) => {
    //             me.directionsRenderer.setDirections(response);
    //         })
    //         .catch((e) => window.alert("Directions request failed due to " + status));
    // }

    getPlaceInformation(placeId) {
        const me = this;

        this.placesService.getDetails({placeId: placeId}, (place, status) => {
            if (
                status === "OK" &&
                place &&
                place.geometry &&
                place.geometry.location
            ) {
                //-----------------------------------------
                restarantInfoParse(place);
                //-----------------------------------------
            }
        });
    }
}

function restarantInfoParse(place){

    const formName = document.getElementById("formName");
    const formAddress = document.getElementById("formAddress");
    const formDescription = document.getElementById("formDescription");



    // Fill in the form fields with place details
    formName.value = place.name;

    // Fill in Location
    formAddress.value = place.formatted_address;

    formDescription.value = place.url ? place.url : "" + " " + place.types ? place.types : "" + " " + place.rating ? place.rating : "" + " Open at:" + extractOpeningHours(place.opening_hours) + " Closes at:" + extractClosingHours(place.opening_hours);

    window.onload = initMap;

}

function returnToUserLocation() {
    if (currentCord) {
        map.setCenter(currentCord);

        // Kiểm tra xem marker của vị trí hiện tại đã tồn tại chưa
        if (currentMarker) {
            // Nếu đã tồn tại, xoá marker cũ
            currentMarker.setMap(null);
        }
        // Tạo mới marker của vị trí hiện tại với animation
        currentMarker = new google.maps.Marker({
            position: currentCord,
            map: map,
            title: 'Vị trí của bạn',
            animation: google.maps.Animation.DROP,
            icon: {
                url: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png', // Thay đổi màu của đánh dấu thành màu xanh
            }
        });
    }
}