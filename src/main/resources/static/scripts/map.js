google.maps.event.addDomListener(window, "load", function(){

    const ubication = new Localization(()=>{
        const myLatandLng = {lat: ubication.latitude, lng: ubication.longitude};
        
        const options = {
            center: myLatandLng,
            zoom: 14
        }
        var map = document.getElementById('map');
        const mapa = new google.maps.Map(map, options); 

        const marker = new google.maps.Marker({
            position: myLatandLng,
            map: mapa,
            title: "Delivery address"
        })

        let information = new google.maps.InfoWindow();
        marker.addListener('click', function(){
            information.open(mapa, marker)
        })

        let autoComplete = document.getElementById('autoComplete');
        const search = new google.maps.places.Autocomplete(autoComplete);
        search.bindTo("bounds", mapa)

        search.addListener('place_changed', function(){
            information.close();
            marker.setVisible(false);

            let place = search.getPlace();

            if(!place.geometry.viewport){
                window.alert("Error showing place");
                return;
            }
            if(place.geometry.viewport){
                mapa.fitBounds(place.geometry.viewport);
            }else{
                mapa.setCenter(place.geometry.viewport);
                mapa.setZoom(18);
            }

            marker.setPosition(place.geometry.location);
            marker.setVisible(true);

            let address = "";
            if(place.address_components){
                address= [
                    (place.address_components[0] && place.address_components[0].short_name || '' ),
                    (place.address_components[1] && place.address_components[1].short_name || '' ),
                    (place.address_components[2] && place.address_components[2].short_name || '' ),
                ]
            }
            information.setContent('<div><strong>'+place.name + '</strong><br>' + address);
            information.open(mapa, marker);

        })
    })
});