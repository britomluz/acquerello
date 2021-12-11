const app = Vue.createApp({
    data() {
      return {
        datetime: "",
        bookingHour: 0,
        sectortime: "",
        table: 0,
        quantitytime: 0,
        disabled : true,
      };
    },
    created() {},
    methods: {
      //   user and admin
      create_booking() {  
        if(this.table<19){
          this.sectortime= "GOLDEN"
        }else if(this.table<31){
          this.sectortime= "PLATINUM"
        }else{
          this.sectortime= "VIP"
        }        
        
        axios.post("/api/booking/create", {
            date: this.datetime,
            bookingHour: this.bookingHour,
            sector: this.sectortime,
            table: this.table,
            quantity: this.quantitytime,
          })
          .then((response) => {
            console.log(response)
            swal({
                title: "Booking succesfull!",
                text: "we will love to see you!",
                icon: "success",
                button: "Ok",
              });
          })
          .catch((err) => console.log(err));
      },
      //   user
      edit_booking() {
        axios.patch("")
          .then((response) => console.log(response))
          .catch((err) => console.log(err));
      },
      delete_booking() {
        axios.delete()
          .then((response) => console.log(response))
          .catch((err) => console.log(err));
      },
      // ADMIN
      patch_booking() {
        axios.patch()
          .then((response) => console.log(response))
          .catch((err) => console.log(err));
      },
      BTN(value){
        this.sectortime = value.target.value        
      }
    },
    computed:{
      tableAvailability(){
        if(booking.type == 'NOTAVAILABLE') {
          this.disabled = true;
      } else  {
          this.disabled = false;
      }
      }
    }
  });
  app.mount("#app");