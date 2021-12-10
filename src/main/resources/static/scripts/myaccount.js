const app = Vue.createApp({
  data() {
    return {
      users: [],
      user:[],
      card:[],
    //   address
      street: "",
      numberAddress: 0,
      zip: "",
      state: "",
      reference: "",
      myaddress:[],
      addrefence: "",
      addstate: "",
      addzip: "",
      addnumber: 0,
      addstreet: "",
    //   order
    order:[]

    };
  },
  created() {
    this.get_user()
    this.load_user()
    this.order_user()
  },
  methods: {
    get_user() {
        axios.get("/api/users")
        .then(response=>{
            console.log(response.data)
            this.users=response.data
        })
        .catch(err=>console.log(err))
    }, 
    load_user(){
        axios.get("/api/users/current")
        .then(res => {
          this.user = res.data
          this.card=res.data.card
         this.myaddress = res.data.address 
          console.log(res.data.address)
      })
        .catch(err => err.message)
    }, 
    send_address() {
        axios
          .post("/api/address/create", {
            street: this.street,
            number: this.numberAddress,
            zip: this.zip,
            state: this.state,
            reference: this.reference,
          })
          .then((response) => alert(response.data))
          .catch((err) => console.log(err.response.data));
      },
      edit_address() {
        axios
          .patch(`/api/address/edit?id=${1}`, {
            street: this.addstreet,
            number: this.addnumber,
            zip: this.addzip,
            state: this.addstate,
            reference: this.addrefence,
          })
          .then((response) => console.log(response))
          .catch((err) => console.log(err.response.data));
      },
      order_user(){
          axios.get("/api/order/current")
          .then(response=>{
            this.order=response.data  
            console.log(response.data)
        })
          .catch(err=>console.log(err))
      }
  },
});
app.mount("#app");
