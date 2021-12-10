const app = Vue.createApp({
  data() {
    return {
      datetime: "",
      bookingHour: 0,
      sectortime: "",
      table: 0,
      quantitytime: 0,
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
      console.log(this.bookingHour)
      console.log(this.datetime)
      console.log(this.table)
      console.log(this.sectortime)
      console.log(this.quantitytime)
      axios
        .post("/api/booking/create", {
          date: this.datetime,
          bookingHour: this.bookingHour,
          sector: this.sectortime,
          table: this.table,
          quantity: this.quantitytime,
        })
        .then((response) => {
          console.log(response)
        })
        .catch((err) => console.log(err));
    },
    //   user
    edit_booking() {
      axios
        .patch("")
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    delete_booking() {
      axios
        .delete()
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    // ADMIN
    patch_booking() {
      axios
        .patch()
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    BTN(value){
      this.sectortime=value.target.value
      console.log(this.sectortime)
      console.log(value.target.value)
    }
  },
});
app.mount("#app");
