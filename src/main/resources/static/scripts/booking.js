const app = Vue.createApp({
    data() {
      return {
        datetime: "",
        bookingHour: 0,
        sectortime: "",
        table: 0,
        quantitytime: 0,
       
        idpacth:0,
        idcal:0,
        // show edit booking
        show:false,
        // get a booking
        bookinget:[],
        // get id for delete booking
        booking_id:0,
        // edit booking
        editdate:"",
        bookniedit:"",
        sectoredit:"",
        tabledit:0,
        quantityedit:0,

        disabled : true,
      };
    },
    created() {
      this.get_users()
    },
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
            swal({
                title: "Booking succesfull!",
                text: "we will love to see you!",
                icon: "success",
                button: "Ok",
              });
          })
          .catch((err) => console.log(err));
      },
      //  edit  user
      edit_booking() {
        this.sectoredit =this.sectortime
        this.tabledit = this.table
        console.log(this.editdate,this.bookniedit,this.sectoredit,this.tabledit,this.quantityedit)
        if(this.tabledit<19){
          this.sectoredit= "GOLDEN"
        }else if(this.tabledit<31){
          this.sectoredit= "PLATINUM"
        }else{
          this.sectoredit= "VIP"
        }
        axios.patch(`/api/booking/edit?id=${this.idpacth}`,{
          date:this.editdate,
          bookingHour:this.bookniedit,
          sector:this.sectoredit,
          table:this.tabledit,
          quantity:this.quantityedit
        })
          .then((response) => console.log(response))
          .catch((err) => console.log(err.response.data));
      },
      // booking cancel
      delete_booking() {
        console.log(this.booking_id)
        axios.get(`/api/booking/cancel?id=${this.booking_id}`)
          .then((response) => alert(response.data))
          .catch((err) => console.log(err));
      },
      // ADMIN
      patch_booking() {
        axios.patch(``)
          .then((response) => console.log(response))
          .catch((err) => console.log(err));
      },
      show_edit(){
        this.show=!this.show
      },
      // change(){
      //   this.editdate=this.datetime
      //   this.bookniedit=this.bookingHour
      //   this.tabledit = this.table
      //   this.quantityedit=this.quantitytime
      // },
      get_users(){
        axios.get("/api/user/current/bookings")
        .then(response=>{
          console.log(response.data)
          this.bookinget=response.data
        })
        .catch(err=>console.log(err))
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