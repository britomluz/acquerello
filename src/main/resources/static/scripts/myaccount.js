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
    order:[],
    idorder:0,

    //add balance card restaurant
    numberCardRest:"",
    cvvCardRest:"",
    vecCardRest:"",
    amountCardRest:"",
    idCardRest:"",
    errorCardRest: false,
    error_cardRest:"",

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
    this.get_user()
    this.load_user()
    this.order_user()

    if (localStorage.getItem("cart")) {
      this.cart = JSON.parse(localStorage.getItem("cart"));
      this.totalQantity = JSON.parse(localStorage.getItem("quantity"));
    }
  },
  methods: {
    get_user() {
        axios.get("/api/users")
        .then(response=>{
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
      })
        .catch(err =>{
          // err.message
          console.log(err.response)  
        } 
        )
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
      edit_address(e) {
        this.id=e.target.parentElement.id
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
      order_cancel_user(e){  
        this.idorder=e.target.parentElement.id
        axios.get(`/api/order/cancel?id=${this.idorder}`)
        .then(response=>{
          console.log(response)
          // alert(response.data)
          swal({
            title: "Are you sure?",
            // text: "Once canceled",
            icon: "warning",
            buttons: true,
            dangerMode: true,
          })
          .then((willDelete) => {
            if (willDelete) {
              swal(response.data, {
                icon: "success",
              })
              .then(respone=>{window.location.reload()})
            } else {
              swal("your order is not cancel");
            }
          });
        })
        .catch(err=>console.log(err))
      },
      show_id(e){
        
        console.log(this.idorder)
      },
      order_user(){
          axios.get("/api/order/current")
          .then(response=>{
            console.log(response)
            this.order=response.data            
        })
          .catch(err=>console.log(err))
      },
      getCardId(e){
        this.idCardRest = e.target.id
        
        console.log(e.target.id)
        console.log(this.idCardRest)

      },
      addBalanceCardRest(e){
          axios.post('https://mindhub-b.herokuapp.com/api/payments', {
            number: this.numberCardRest,
            cvv: this.cvvCardRest,
            amount: this.amountCardRest,
            description: "Add balance Acquerello Card",
            accountNumber: "VIN-64578965",
          })
          .then(res => {
            axios.patch(`/api/cards/add-balance/${this.idCardRest}?amount=${this.amountCardRest}`)
          })
          .then(res =>{
            swal({
              title: "Good job!",
              text: "You add balance to Acquerello Card",
              icon: "success",
              button: "OK",
            });
          })
          .catch(error =>{
            console.log(error.response.data)

            this.errorCardRest = true
            this.error_cardRest = error.response.data
          })
      },
      createCard() {
        axios.post('/api/cards/create')
            .then(() => swal('Card created'))
            .then(console.log("Card created"))
            .catch(err => console.log(err))
    },
    showOrderDetails(e) {
      let id = e.target.parentElement.id
      window.location.href = `./order-details.html?id=${id}`

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
        if (clickEvent.target.id == product.id) {
          product.quantity++;
          product.stock--;
          this.totalQantity++
          localStorage.setItem("cart", JSON.stringify(this.cart));
          localStorage.setItem("quantity", JSON.stringify(this.totalQantity));
        }
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

      logout(){
        axios.get("/api/logout")
        .then(res=>{
          console.log(res)
          window.location.href="/web/login.html"
        })
        .catch(err=>console.log(err))
      }
  },
});
app.mount("#app");
