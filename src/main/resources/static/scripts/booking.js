const app = Vue.createApp({
  data() {
    return {
      datetime: "",
      bookingHour: 0,
      sectortime: "",
      table: 0,
      quantitytime: 0,
      // cancel booking user
      idcal: 0,
      // show edit booking
      show: false,
      // get a booking
      bookinget: [],
      // get id for delete booking
      booking_id: 0,
      // edit booking
      idpacth: [],
      editdate: [],
      bookniedit: "",
      sectoredit: "",
      tabledit: 0,
      quantityedit: 0,
      // edit value to edit in booking
      valueid: 0,
      // booking only
      onlybooking:[],
      // status booking
      statusbooking:"",
      // disabled table 
      disabled: 0,

      bookingsUsers: [],
      // filter sector
      statesfilter:[],
      // status filter
      sectorfilter:[],
      // hours filter
      hoursfilter:[],
      // data filter
      yearfilter:[],
      // monthfilter
      monthfilter:[],
      // day filter
      dayfilter:[],

       //cart
       qantity: "",
       totalQantity: 0,
       product: [],
       destacados: [],
       cart: [],
       modal: [],
       search: "",
       quantity: [],
       searchProducts: false,
       show: false,
    };
  },
  created() {
    this.get_users();
    this.get_bookings();

    if (localStorage.getItem("cart")) {
      this.cart = JSON.parse(localStorage.getItem("cart"));
      this.totalQantity = JSON.parse(localStorage.getItem("quantity"));
    } 
  },
  methods: {
    //   user and admin    
    create_booking() {
      console.log(this.datetime, this.bookingHour, this.sectortime, this.table, this.quantitytime)
      if (this.table < 19) {
        this.sectortime = "GOLDEN";
      } else if (this.table < 31) {
        this.sectortime = "PLATINUM";
      } else {
        this.sectortime = "VIP";
      }

      this.table = parseInt(this.table)
      
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
        .catch((err) =>{
          console.log(err.response.data)
        });
    },
    // cancel user booking
    cancel_booking(e){
      this.idcal=this.bookinget.filter(booking=>booking.id==e.target.parentElement.id)
      axios.get(`/api/booking/cancel?id=${this.idcal[0].id}`)
      .then(response=>{
        alert(response.data)
        setTimeout(() => {
          window.location.reload()
        }, 200);

      })
      .catch(err=>console.log(err.response))
    },
    //  edit  user
    edit_booking() {
      axios
        .patch(`/api/booking/edit?id=${this.idpacth}`, {
          date: this.editdate,
          bookingHour: this.bookniedit,
          sector: this.sectoredit,
          table: this.tabledit,
          quantity: this.quantityedit,
        })
        .then((response) => {
          alert(response.data)
        })
        .catch((err) => console.log(err.response.data));
    },
    // show edit booking
    show_edit(e) {
      this.onlybooking = this.bookingsUsers.filter(booking=> booking.id==e.target.parentElement.id)
      this.idpacth = this.onlybooking[0].id
      this.editdate = this.onlybooking[0].dateBooking;
      this.bookniedit = this.onlybooking[0].bookingHour;
      this.tabledit = this.onlybooking[0].tableNumber;
      this.sectoredit=this.onlybooking[0].sector
      this.statusbooking=this.onlybooking[0].state
      this.quantityedit = this.onlybooking[0].quantity;
      this.show = !this.show;
    },
    change(){
      this.show = !this.show;
    },
    get_users() {
      axios
        .get("/api/user/current/bookings")
        .then((response) => {
          this.bookinget = response.data;
          this.bookinget = response.data.sort((a,b) => parseInt(a.id - b.id))
        })
        .catch((err) => console.log(err));
    },
    get_bookings() {
      axios
        .get("/api/booking")
        .then((response) => {
          this.bookingsUsers = response.data;
          console.log(this.bookingsUsers)
        })
        .catch((err) => console.log(err));
    },
    editBookingState(e) {
      let bookingId = e.target.firstChild.id;
      let bookingState = e.target.firstChild.value;
      console.log(bookingId);
      console.log(bookingState);

      axios
        .patch(
          "/api/admin/booking/edit",
          `id=${bookingId}&type=${bookingState}`
        )
        .then((res) => {
          console.log(res);
          window.location.reload();
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //cart
    deleteOne(clickEvent) {
      this.cart.forEach((product) => {
        if (clickEvent.target.id == product.id) {
          product.quantity--;
          product.stock++;
          this.totalQantity--
        }
      });
      this.cart.forEach((product, i) => {
        if (product.quantity == 0) {
          this.cart.splice(i, 1);
        }
        localStorage.setItem("cart", JSON.stringify(this.cart));
        localStorage.setItem("quantity", JSON.stringify(this.totalQantity));
      });
    }, 
    addOne(clickEvent) {
      this.cart.forEach((product) => {
        if (clickEvent.target.id == product.id){ 
          product.quantity++;
        product.stock--;
        this.totalQantity++
        localStorage.setItem("cart", JSON.stringify(this.cart));
        localStorage.setItem("quantity", JSON.stringify(this.totalQantity));}
      });
    }, 
    calculateTotal() {
      let total = 0;
      this.cart.forEach((product) => {
        total += product.price * product.quantity;
      });
      return total;
    },
    emptyCart() {
      this.cart.forEach(product => product.quantity = 0)
      this.cart = [];
      this.totalQantity = 0
      localStorage.setItem("cart", JSON.stringify(this.cart));
      localStorage.setItem("quantity", JSON.stringify(this.totalQantity));
    }, 
  },
  computed: {
    tableAvailability() {
      if (this.bookingsUsers.tableAvailability == "NOTAVAILABLE") {
        this.disabled = this.disabled + 1;
      } else {
        this.disabled = this.disabled;
      }
      console.log(this.disabled);
    },
    filterBookings(){
      let a = this.bookingsUsers
      return this.bookingsUsers.filter(booking=>this.statesfilter.includes(booking.sector)||this.statesfilter.length === 0)
                                .filter(booking=>this.sectorfilter.includes(booking.state)||this.sectorfilter.length===0)
                                .filter(booking=>booking.bookingHour.slice(0,4).match(this.hoursfilter))
                                .filter(booking=>booking.dateBooking.slice(0,4).match(this.yearfilter))
                                .filter(booking=>booking.dateBooking.slice(5,7).match(this.monthfilter))
                                .filter(booking=>booking.dateBooking.slice(8,10).match(this.dayfilter))
    },
   

  },
});
let obv=app.mount("#app");
