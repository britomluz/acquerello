const App = Vue.createApp({
  data() {
    return {
      firstname: {
        input: "",
        msg: "",
        correct: false,
      },
      email: {
        input: "",
        msg: "",
        correct: false,
      },
      lastname: {
        input: "",
        msg: "",
        correct: false,
      },
      password: {
        input: "",
        msg: "",
        correct: false,
      },
      password_two: {
        input: "",
        msg: "",
        correct: false,
      },
      email_two: {
        input: "",
        msg: "",
        correct: false,
      },
      phone: {
        input: "",
        msg: "",
        correct: false,
      },

      //filter orders admin
      filterDescription: '',
      filterType: [],
      filterRange: 50000,
      filterDay: '',
      filterMonth: '',
      filterYear: '',

      //filter orders user
      filterDescriptionUser: '',
      filterTypeUser: [],
      filterRangeUser: 50000,
      filterDayUser: '',
      filterMonthUser: '',
      filterYearUser: '',

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
   
    if (localStorage.getItem("cart")) {
      this.cart = JSON.parse(localStorage.getItem("cart"));
      this.totalQantity = JSON.parse(localStorage.getItem("quantity"));
    }
  },
  methods: {
    validator_firstname(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (data.input.search(/^[a-zA-Z\s]*$/)) {
        data.msg = "field must be only letter";
        data.correct = false;
      } else if (data.input.length > 8) {
        data.msg = "firstname field should be less than 8 character";
        data.correct = false;
      } else if (data.input.length < 1) {
        data.msg = "firstname field should be more than 1 character";
        data.correct = false;
      } else {
        data.msg = "field is correct";
        data.correct = true;
      }
    },
    validator_lastname(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (data.input.search(/^[a-zA-Z\s]*$/)) {
        data.msg = "field must be only letter";
        data.correct = false;
      } else if (data.input.length > 8) {
        data.msg = "lastname field should be less than 8 character";
        data.correct = false;
      } else if (data.input.length < 1) {
        data.msg = "lastname field should be more than 1 character";
        data.correct = false;
      } else {
        data.msg = "field is correct";
        data.correct = true;
      }
    },
    validator_emial(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (
        data.input.search(
          /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/
        )
      ) {
        data.msg = "mail field is not formatted";
        data.correct = false;
      } else {
        data.msg = "mail field is correct";
        data.correct = true;
      }
    },
    validator_emial_2(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (
        data.input.search(
          /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/
        )
      ) {
        data.msg = "mail field is not formatted";
        data.correct = false;
      } else {
        data.msg = "mail field is correct";
        data.correct = true;
      }
    },
    validator_password(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (data.input.search(/[A-Za-z0-9!?-]{8,20}/)) {
        data.msg = "password must have between 8 and 20 characters";
        data.correct = false;
      } else {
        data.msg = "password field is correct";
        data.correct = true;
      }
    },
    validator_password_2(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (data.input.search(/[A-Za-z0-9!?-]{8,20}/)) {
        data.msg = "password must have between 8 and 20 characters";
        data.correct = false;
      } else {
        data.msg = "password field is correct";
        data.correct = true;
      }
    },
    validator_phone(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (
        data.input.match(
          /^[+]?(1\|1\s|1|\d{3}\|\d{3}\s|)?((\(\d{3}\))|\d{3})(\|\s)?(\d{3})(\|\s)?(\d{4})$/g
        )
      ) {
        //does not accept format 0 but + is accepted just fill in field
        data.msg = "phone field is correct";
        data.correct = true;
      } else {
        data.msg = "only number";
        data.correct = false;
      }
    },
    send() {
        axios.post("/api/users/create", {
          firstName: this.firstname.input,
          lastName: this.lastname.input,
          email: this.email.input,
          password: this.password.input,
          number: this.phone.input,
        })
          .then(response => {
            window.location.href = "/web/myaccount.html"
            this.login(this.email.input,this.password.input)
        })
          .catch(err => {
            swal({
              title: err.response.data,
              icon: "warning", 
            })  
          })
    },
    login(email,password) {
      axios.post("/api/login", `email=${email}&password=${password}`)
        .then(response => window.location.href = "/web/myaccount.html")
        .catch(err => {
          let a = err.response.data.error;
          swal({
            title: a,
            icon: "warning", 
          })  
        })
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
   
    
  },

  computed: {
    filterOrdersAdmin() {
      this.orders.filter(order => order.description.toLowerCase().match(this.filterDescription.toLowerCase()))
        .filter(order => this.filterType.includes(order.type) || this.filterType.length === 0)
        .filter(order => order.amount <= this.filterRange)
        .filter(order => order.date.slice(0, 2).match(this.filterDay))
        .filter(order => order.date.slice(3, 5).match(this.filterMonth))
        .filter(order => order.date.slice(6, 10).match(this.filterYear))
    },
    filterOrdersUser() {
      this.ordersUser.filter(order => order.description.toLowerCase().match(this.filterDescriptionUser.toLowerCase()))
        .filter(order => this.filterTypeUser.includes(order.type) || this.filterTypeUser.length === 0)
        .filter(order => order.amount <= this.filterRangeUser)
        .filter(order => order.date.slice(0, 2).match(this.filterDayUser))
        .filter(order => order.date.slice(3, 5).match(this.filterMonthUser))
        .filter(order => order.date.slice(6, 10).match(this.filterYearUser))
    },
    /* prueba(){
      let side1 = this.$refs.side1
      let side2 = this.$refs.side1
        side1.style.left = "-window.pageYOffset" + 'px'
        side2.style.left = "window.pageYOffset" + 'px'
      let menu = this.$refs.navbar
      menu.classList.toggle('fondoBlack', window.scrollY > 800)
      console.log(side1, side2, menu)
    } */

  }
});

App.mount("#app");
