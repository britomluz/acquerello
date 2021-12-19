// fill tables
let test = /\b[checkout]+\b/g
let url = window.location.href;
let match = url.match(test);
if (match != null) {
  const select = document.getElementById("table");
  for (let i = 1; i <= 30; i++) {
    const opt = document.createElement("option");
    opt.value = i;
    opt.innerText = `Nº ${i}`
    select.appendChild(opt)
  }
}

const app = Vue.createApp({
  data() {
    return {
      products: [],
      productId: "",
      product: [],
      productName: "",
      productImage: "",
      price: "",
      stock: [],
      ordername: "",
      orderquantity: 0,
      priceorder: 0,
      totalorder: 0,
      categories: [],
      card:[],

      //product categories
      mainCourses: [],
      entriesSnacks: [],
      specials: [],
      chefPicks: [],
      soups: [],
      drinks: [],
      pastas: [],
      vegetarians: [],
      salads: [],

      // filter products
      filterNameProduct: "",

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
      table: false,

      //multistep form
      prevBtns: "",
      nextBtns: "",
      progress: "",
      formSteps: "",
      progressSteps: [],
      progressActive: "",

      // save orders step by step

      firstName: "",
      lastName: "",
      phone: "",
      email: "",
      password: "",

      addessSelected: [],

      street: "",
      number: "",
      zip: "",
      reference: "",
      state: "",
      addressId: "",
      userCardAcquerello: "",

      numberCard: "",
      cvv: "",
      vec: "",
      accountNumber: "VIN-64578965",
      description: "Accquerello order",
      tableNumber: "",

      total: 0,
      totalProduct: 0,
      type: "",

      deliver: false,

      logged: false,
      guest: false,
      user: "",
      address: [],
      show_address: "",
      addressInfo:"",

      // payment ways

      acquerelloCard: false,
      bankCard: true,
      cardPay:"bank",
      // conteo de peticiones exitosas

      requests: {
        createUser: false,
        createAddress: false,
      },
      // edit product
      nameeditproduct: "",
      descriptionproduc: "",
      productimg: "",
      priceedit: 0,
      stockedit: 0,
      showbtn: false,
      errorEditProduct: false,
      error_editProduct: "",
      // add product
      products_img: "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
      nameproducts: "",
      descriptionproducts: "",
      priceproduct: 0,
      stockproduct: 0,
      id_addproduct: 0,
      errorAddProduct: false,
      error_addProduct: "",

      //shop
      cardNumber: "",
      cardTypeCheck: "",

      // error div
      errorCardRest: false,
      error_cardRest: "",

      //image products
      CLOUDINARY_URL: "",
      CLOUDINARY_UPLOAD_PRESET: "",
      urlImg: "",
      imagePreviewDos: "",
      imageUploaderDos: "",

      //error checkout
      errorCheckout:false,
      error_msgCheckout:"",

    };
  },
  created() {
    //this.Admin_accept_order()
    this.totalQantity = 0
    this.loadProducts();
    this.loadCategories();
    this.loadDataProduct();


    this.showModal();
    if (localStorage.getItem("cart")) {
      this.cart = JSON.parse(localStorage.getItem("cart"));
      this.totalQantity = JSON.parse(localStorage.getItem("quantity"));
      this.addToCart();
    }

    this.cart.forEach((c) => {
      this.total += c.price * c.quantity;
    });
    this.nameeditproduct = this.product.name;

    let p = JSON.parse(localStorage.getItem("cart"));

    if (p != null) {
      p.forEach(p => {
        this.totalProduct += p.price * p.quantity
      })
    }

    // check if a user is logged
    axios.get("/api/users/current").then(res => {
      if (res.status === 202) {
        this.logged = true;
        this.user = res.data;
        this.card = res.data.card[0]

        //console.log(this.card)
        this.address = res.data.address;

        if (res.data.card.length > 0) {
          this.userCardAcquerello = res.data.card[0].id;
        }
      }
    }).catch(err => {
      console.log(err.response)
      if (err.response.status === 401) {
        this.logged = false;
        this.guest = true;
      }
    })
  },
  methods: {
    loadProducts() {
      axios
        .get("/api/products")
        .then((response) => {
          this.products = response.data.products;
        })
        .catch((err) => console.log(err.response.data));
    },
    loadCategories() {
      axios
        .get("/api/categories")
        .then((response) => {
          this.categories = response.data.categories;
          this.entriesSnacks = [...this.categories.filter(
            (categorie) => categorie.name === "Entries & Snacks")[0].products].sort((a, b) => parseInt(a.id - b.id));
          this.specials = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Specials"
            )[0].products,
          ].sort((a, b) => parseInt(a.id - b.id));
          this.chefPicks = [...this.categories.filter((categorie) => categorie.name === "Chef Picks")[0].products].sort((a, b) => parseInt(a.id - b.id));
          this.mainCourses = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Main Course"
            )[0].products,
          ].sort((a, b) => parseInt(a.id - b.id));
          this.soups = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Soup"
            )[0].products,
          ].sort((a, b) => parseInt(a.id - b.id));
          this.drinks = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Drinks"
            )[0].products,
          ].sort((a, b) => parseInt(a.id - b.id));
          this.pastas = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Pasta"
            )[0].products,
          ].sort((a, b) => parseInt(a.id - b.id));
          this.vegetarians = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Vegetarian"
            )[0].products,
          ].sort((a, b) => parseInt(a.id - b.id));
          this.salads = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Salads"
            )[0].products,
          ].sort((a, b) => parseInt(a.id - b.id));
        })
        .catch((err) => console.log(err));
    },
    createProduct() {
      axios.post("/api/products/create", (idCategory = productId), {
        //ren, I added {} because I got an error

        name: this.productName,
        description: this.description,
        productImage: this.productImage,
        price: this.price,
        stock: this.stock,
      })
        .then((res) => {
          console.log("Product created");
          swal({
            title: "Good job!",
            text: "You clicked the button!",
            icon: "success",
            button: "Aww yiss!",
          });
        })
        .catch((err) => console.log(err));
    },
    deleteProduct() {
      axios
        .delete(`/api/products/delete/${this.id}`)
        .then(console.log("Product delete"))
        .catch((err) => console.log(err));
    },
    editProduct() {
      axios.patch(`/api/products/edit/${this.id}`, {
        //ren, I added {} because I got an error
        name: this.productName,
        description: this.description,
        productImage: this.productImage,
        price: this.price,
        stock: this.stock,
      })
        .then(console.log("Product Edit"))
        .catch((err) => console.log(err));
    },

    Edit_order() {
      axios
        .patch(`/api/order/edit/${this.idOrder}`)
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    Delete_order() {
      axios
        .delete(`/api/order/cancel/${this.idOrder}`)
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    Admin_accept_order() {
      axios
        .get(`/api/order/confirm/${this.idOrder}`)
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    user_Accept_order() {
      axios
        .post("/api/order/buy", {
          name: this.ordername,
          quantity: this.orderquantity,
          price: this.priceorder,
          total: this.totalorder,
        })
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    addToCart(id) {
      this.totalPrice = 0;

      for (let i = 0; i < this.products.length; i++) {
        if (this.products[i].id == id) {
          // si no esta en el carrito lo agrega
          if (!this.cart.includes(this.products[i])) {
            this.products[i].quantity++;
            this.cart.push(this.products[i]);
            this.totalQantity++
          } else {
            // de lo contraria le suma +1 a la cantidad del producto
            this.products[i].quantity++;
            this.totalQantity++

          }
          this.totalPrice = this.totalPrice + this.products[i].price;

        }
      }
      localStorage.setItem("cart", JSON.stringify(this.cart));
      localStorage.setItem("quantity", JSON.stringify(this.totalQantity));
    },
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
    showModal(id) {
      let prod = this.products.filter((item) => item.id == id);
      this.modal = prod;
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
    //multistep form
    updateForms(btn, form1, form2, step) {
      this.nextStep(btn, form1, form2);
      this.nextProgressBar(btn, step);
    },
    backForms(btn, form1, form2, step) {
      this.prevStep(btn, form1, form2);
      this.prevProgressBar(btn, step);
    },
    nextStep(btn, form1, form2) {
      //this.paymentsList()

      this.nextBtns = btn; //next1

      this.formSteps1 = form1; //form1
      this.formSteps2 = form2; //form2

      this.formSteps1.classList.remove("form-step-active");
      this.formSteps2.classList.add("form-step-active");
    },
    prevStep(btn, form1, form2) {
      this.prevBtns = btn; //next1

      this.formSteps1 = form1; //form2
      this.formSteps2 = form2; //form1

      this.formSteps1.classList.add("form-step-active");
      this.formSteps2.classList.remove("form-step-active");
    },
    nextProgressBar(btn, step) {
      this.progressSteps1 = step;
      this.progressSteps1.classList.add("progress-step-active");

      this.progress = this.$refs.progress;
      this.progressActive = "progress-step-active";

      if (this.progressSteps1 == this.$refs.progressStep2) {
        this.progress.style.width = "50%";
      } else if (this.progressSteps1 == this.$refs.progressStep3) {
        this.progress.style.width = "100%";
      }
    },
    prevProgressBar(btn, step) {
      this.progressSteps1 = step;
      this.progressSteps1.classList.remove("progress-step-active");

      this.progressActive = "progress-step-active";

      if (this.progressSteps1 == this.$refs.progressStep2) {
        this.progress.style.width = "0%";
      } else if (this.progressSteps1 == this.$refs.progressStep3) {
        this.progress.style.width = "50%";
      }

      // this.errorLoan = false
    },
    show_address_select(e) {
      if (e.target.value == "newAdress") {
        this.show = true;
        const div = document.getElementById("check")
        div.style.height = "1000px";
        div.style.transition = "height 1s ease"
        this.addressId = 0;
      } else {
        this.show = false;
        this.addressId = e.target.value;

        const div = document.getElementById("check")
        div.style.height = "auto"
      }
    },
    send_address() {
      axios
        .post("/api/address/create", {
          street: this.street,
          number: this.numberAddress,
          zip: this.zip,
          state: "califormia",
          reference: this.reference,
        })
        .then((response) => alert(response.data))
        .catch((err) => console.log(err.response.data));
    },
    confirmBuy() {
      // format data
      let tel = Number(this.phone);
      let number = Number(this.number);
      let total = Number(this.totalProduct);
      let cvv = Number(this.cvv)

      const list = JSON.parse(localStorage.getItem("cart"))

      let product = [];


      if (list != null) {
        list.forEach(l => {
          let obj = {
            description: l.description,
            id: l.id,
            name: l.name,
            price: l.price,
            productImage: l.productImage,
            quantity: l.quantity,
            stock: l.stock
          }
          product.push(obj)
        })

        // if an authenticated user send a request
        if (this.logged) {
          let spinner = this.$refs.spinnerContainer
          spinner.style.display = 'flex';

          axios.post("/api/order/buy", {
            products: product, total: total, type: this.type, id: this.addressId, street: this.street,
            numberStreet: number,
            zip: this.zip,
            state: this.state,
            reference: this.reference,
            tableNumber: this.tableNumber,
          }).then(res => {
            // create payment
            let orderId = res.data.slice(9);
            spinner.style.display = 'none';

            // if the user selected bank card
            if (this.cardTypeCheck == "bank") {
              console.log(this.cardTypeCheck)
              spinner.style.display = 'flex';
              axios.post("https://mindhub-b.herokuapp.com/api/payments", { number: this.numberCard, cvv: cvv, amount: total, description: this.description, accountNumber: this.accountNumber }).then(res => {
                spinner.style.display = 'none';
                swal({
                  title: "Good job!",
                  text: "Payment succesfull!",
                  icon: "success",
                  button: "Ok",
                }).then(res => {
                  axios.post(`/api/sendMail?id=${orderId}`)
                  window.location.href = "/web/my-orders.html"
                });
                localStorage.clear();
              }).catch(err => {
                this.errorCardRest = true;
                spinner.style.display = 'none';

                if (err.response.status == 500) {
                  this.error_cardRest = "Check empty fields";
                } else {
                  this.error_cardRest = err.response.message;
                }

                setTimeout(() => {
                  this.errorCardRest = false;
                  this.error_cardRest = ""
                }, 3000)
              })
            } else {
              console.log(this.cardTypeCheck)
              spinner.style.display = 'flex';
              let id = Number(this.userCardAcquerello)
              let total = Number(this.totalProduct);

              axios.post(`/api/payments/${id}?amount=${total}`).then(res => {
                console.log(res)
                spinner.style.display = 'none';
                localStorage.clear();
                axios.post(`/api/sendMail?id=${orderId}`)
              swal({
                title: "Payment succesfull!",
                text: "Yumm!",
                icon: "success",
                button: "Ok",
              }).then(res => {
                window.location.href = "/web/my-orders.html"
              });
              }).catch(err => {
                console.log(err.response)
                spinner.style.display = 'none';
              })
            }

          }).catch(err => {
            this.errorCardRest = true;

            if (err.response.status == 500) {
              this.error_cardRest = "Check empty fields";
            } else {
              this.error_cardRest = err.response.message;
            }

            setTimeout(() => {
              this.errorCardRest = false;
              this.error_cardRest = ""
            }, 3000)
          })
        } else {
          // create user and address and order
          let spinner = this.$refs.spinnerContainer
          spinner.style.display = 'flex';
          axios
            .post("/api/order/checkout", {
              firstName: this.firstName,
              lastName: this.lastName,
              email: this.email,
              password: this.password,
              number: tel,

              street: this.street,
              numberStreet: number,
              zip: this.zip,
              state: this.state,
              reference: this.reference,

              products: product,
              total: total,
              type: this.type,
              tableNumber: this.tableNumber
            })
            .then((res) => {
              let orderId = res.data.slice(9);
              console.log(orderId)

              // create payment

              axios.post("https://mindhub-b.herokuapp.com/api/payments", { number: this.numberCard, cvv: cvv, amount: total, description: this.description, accountNumber: this.accountNumber }).then(res => {
                console.log(res)
                spinner.style.display = 'none';
                swal({
                  title: "Payment succesfull!",
                  text: "Account registered!",
                  icon: "success",
                  button: "Ok",
                }).then(res => {

                  axios.post(`/api/login?email=${this.email}&password=${this.password}`).then(res => {
                    window.location.href = "/web/myaccount.html"
                    axios.post(`/api/sendMail?id=${orderId}`)
                      .then(res => res)
                  }).catch(err => {
                    console.log(err.response)
                  })

                  console.log(res.data)
                  //window.location.href = "/web/login.html"
                });
                localStorage.clear();
              }).catch(err => {
                this.errorCardRest = true;

                if (err.response.status == 500) {
                  this.error_cardRest = "Check empty fields";
                } else {
                  this.error_cardRest = err.response.message;
                }

                setTimeout(() => {
                  this.errorCardRest = false;
                  this.error_cardRest = ""
                }, 3000)
              })
            })
            .catch((err) => {
              this.errorCardRest = true;

              if (err.response.status == 500) {
                this.error_cardRest = "Check empty fields";
              } else {
                this.error_cardRest = err.response.message;
              }

              setTimeout(() => {
                this.errorCardRest = false;
                this.error_cardRest = ""
              }, 3000)
            });
        }
      }
    },
    register(e) {
      const button = document.getElementById("confirm");

      switch (e.target.type) {
        case "email":
          let verify = /[a-zA-Z0-9_.]+@[a-z]+[.][a-z]{2,}/;
          let match = e.target.value.match(verify);

          if (match !== null) {
            if (e.target.value.length == match.input.length) {
              button.disabled = false;
            } else {
              button.disabled = true;
            }
          } else {
            button.disabled = true;
          }
          break;
      }
    },
    selectRadio(e) {
      switch (e.target.value) {
        case "delivery":
          this.deliver = true;
          this.table = false;
          this.type = "DELIVERY"
          break;
        case "in":
          this.deliver = false;
          this.table = true;
          this.type = "LOCAL"
          break;
        case "withdraw":
          this.deliver = false;
          this.table = false;
          this.type = "TAKEAWAY";
          break;
      }
    },
    loadDataProduct() {
      const urlParam = new URLSearchParams(window.location.search);
      const id = urlParam.get("id");

      axios
        .get(`/api/user/current/products/${id}`)
        .then((res) => {
          this.product = res.data;
        })
        .catch((err) => err.message);
    },
    showProduct(e) {
      let id = e.target.parentElement.id;

      window.location.href = `./product-details.html?id=${id}`;
    },
    // edit products
    pacth_product() {
      //this.productimg = this.product.productImage;
      console.log(this.product)
      axios
        .patch(`/api/products/edit/${this.product.id}`, {
          name: this.nameeditproduct,
          description: this.descriptionproduc,
          productImage: this.products_img,
          price: this.priceedit,
          stock: this.stockedit,
        })
        .then((response) => {
          console.log('hecho')
          console.log(response)
          swal({
            title: "Good job!",
            text: "Product was edited succesfully!",
            icon: "success",
            button: "Ok",
          })
            .then(res => window.location.reload())

        })
        .catch((err) => console.log(err.response));
      this.errorEditProduct = true
      this.error_editProduct = err.response.data
    },
    // show btn in pag product-details
    show_btn() {
      this.nameeditproduct = this.product.name;
      this.descriptionproduc = this.product.description
      this.priceedit = this.product.price
      this.stockedit = this.product.stock
      this.showbtn = !this.showbtn;
    },
    // product add
    product_add() {
      axios
        .post("/api/products/create", {
          idCategory: this.id_addproduct,
          name: this.nameproducts,
          description: this.descriptionproducts,
          productImage: this.products_img,
          price: this.priceproduct,
          stock: this.stockproduct,
        })
        .then((response) => {
          console.log(response)
          swal({
            title: "Good job!",
            text: "Product added succesfully!",
            icon: "success",
            button: "Ok",
          })
            .then(res => window.location.reload())

        })
        .catch((err) => {
          console.log(err.response.data)
          this.errorAddProduct = true
          this.error_addProduct = err.response.data
        });
    },
    selectPayment(e) {
      switch (e.target.id) {
        case "acquerello":
          this.acquerelloCard = true;
          this.bankCard = false;
          this.cardTypeCheck = e.target.id;
          break;
        case "bank":
          this.acquerelloCard = false;
          this.bankCard = true;
          this.cardTypeCheck = e.target.id;
          break;
      }
    },
    uploadImageProduct(event) {
      this.imagePreviewDos = this.$refs.imagePreviewDos
      this.imageUploaderDos = this.$refs.imageUploaderDos
      this.CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/luz-brito/image/upload'
      this.CLOUDINARY_UPLOAD_PRESET = 'qda9s173'

      console.log(event.target.files[0])
      const fileImg = event.target.files[0]

      const formData = new FormData;

      formData.append('file', fileImg)
      formData.append('upload_preset', this.CLOUDINARY_UPLOAD_PRESET)

      axios.post(this.CLOUDINARY_URL, formData, { headers: { 'Content-Type': 'multipart/form-data' } })
        .then(res => {
          console.log("Imagen cargada!")
          console.log(res)
          this.imagePreviewDos.src = res.data.secure_url

          this.products_img = this.imagePreviewDos.src
          this.productsimg = this.imagePreviewDos.src


          //console.log(this.products_img)

        })

    },
    logout() {
      axios.get("/api/logout")
        .then(res => {
          console.log(res)
          window.location.href = "/web/login.html"
        })
        .catch(err => console.log(err))
    }
  },
  computed: {
    filterProducts() {
      return this.products.filter((product) => product.name.toLowerCase().match(this.filterNameProduct.toLowerCase())
      );
    },
    showAddress() {
      this.show_address = orderDetailId[0].address            
    },
    confirm_btn(){
      if(this.logged){ 
        if(this.cardTypeCheck== ''){
          return true
        }
        if(this.cardTypeCheck =='bank'){
          if(this.numberCard =='' || this.cvv =='' || this.vec==''){
            return true
          }
        }
        if(this.type == 'LOCAL'){
          if(this.tableNumber == ''){
            return true
          }
          return false
        }
        if(this.type == 'DELIVERY'){
          if(this.street == '' || this.number == '' || this.state == '' || this.zip == ''){
            return true
          }
          return false
        }
      }else{
        if(this.cardTypeCheck ==''){
          return true
        }
        if(this.cardTypeCheck =='bank'){
          if(this.numberCard =='' || this.cvv =='' || this.vec==''){
            return true
          }
          return false
        }
        if(this.firstName ==''||this.lastName==''||this.phone ==''){
          return true
        }
        if(this.type == 'LOCAL'){
          if(this.tableNumber == ''){
            return true
          }
          return false
        }
        if(this.email==''||this.password==''){
          return true
        }
        if(this.type == 'DELIVERY'){
          if(this.street == '' || this.number == '' || this.state == '' || this.zip == ''){
            return true
          }
          return false
        }
      }
      return false
    }
  },
});
app.mount("#app");
