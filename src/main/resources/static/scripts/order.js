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
      totalQantity: "",
      product: [],
      destacados: [],
      cart: [],
      modal: [],
      search: "",
      quantity: [],
      searchProducts: false,
      show: false,

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

      numberCard: "",
      cvv: "",
      vec: "",

      total: 0,

      deliver: false,

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
      // add product
      products_img: "",
    };
  },
  created() {
    //this.Admin_accept_order()
    this.loadProducts();
    this.loadCategories();
    this.loadDataProduct();

    this.showModal();
    if (localStorage.getItem("cart")) {
      this.cart = JSON.parse(localStorage.getItem("cart"));
      this.addToCart();
    }

    this.cart.forEach((c) => {
      this.total += c.price * c.quantity;
    });
    this.nameeditproduct = this.product.name;
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
          this.entriesSnacks = [ ...this.categories.filter(
              (categorie) => categorie.name === "Entries & Snacks" )[0].products].sort((a,b) => parseInt(a.id - b.id));
          this.specials = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Specials"
            )[0].products,
          ].sort((a,b) => parseInt(a.id - b.id));
          this.chefPicks = [ ...this.categories.filter( (categorie) => categorie.name === "Chef Picks" )[0].products].sort((a,b) => parseInt(a.id - b.id));
          this.mainCourses = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Main Course"
            )[0].products,
          ].sort((a,b) => parseInt(a.id - b.id));
          this.soups = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Soup"
            )[0].products,
          ].sort((a,b) => parseInt(a.id - b.id));
          this.drinks = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Drinks"
            )[0].products,
          ].sort((a,b) => parseInt(a.id - b.id));
          this.pastas = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Pasta"
            )[0].products,
          ].sort((a,b) => parseInt(a.id - b.id));
          this.vegetarians = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Vegetarian"
            )[0].products,
          ].sort((a,b) => parseInt(a.id - b.id));
          this.salads = [
            ...this.categories.filter(
              (categorie) => categorie.name === "Salads"
            )[0].products,
          ].sort((a,b) => parseInt(a.id - b.id));
        })
        .catch((err) => console.log(err));
    },
    createProduct() {
      axios
        .post("/api/products/create", (idCategory = productId), {
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
      axios
        .patch(`/api/products/edit/${this.id}`, {
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
      this.totalQantity = 0;

      for (let i = 0; i < this.products.length; i++) {
        if (this.products[i].id == id) {
          // si no esta en el carrito lo agrega
          if (!this.cart.includes(this.products[i])) {
            let obj = {
              quantity: 1,
            };
            let result = Object.assign(this.products[i], obj);
            this.cart.push(this.products[i]);
            this.totalQantity = this.totalQantity + 1;
          } else {
            // de lo contraria le suma +1 a la cantidad del producto
            this.products[i].quantity++;
          
          }
          this.totalPrice = this.totalPrice + this.products[i].price;
          //this.totalQantity = this.totalQantity + 1;
        }
      }
      localStorage.setItem("cart", JSON.stringify(this.cart));
    },
    deleteOne(clickEvent) {
      this.cart.forEach((product) => {
        if (clickEvent.target.id == product.id) {
          product.quantity--;
          product.stock++;
        }
      });
      this.cart.forEach((product, i) => {
        if (product.quantity == 0) {
          this.cart.splice(i, 1);
        }
        localStorage.setItem("cart", JSON.stringify(this.cart));
      });
    },
    addOne(clickEvent) {
      this.cart.forEach((product) => {
        if (clickEvent.target.id == product.id) product.quantity++;
        product.stock--;
        localStorage.setItem("cart", JSON.stringify(this.cart));
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
      this.cart = [];
      localStorage.setItem("cart", JSON.stringify(this.cart));
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
    show_address(e) {
      if (e.target.value == "newAdress") {
        this.show = true;
      } else {
        this.show = false;
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
    sendForm(e) {
      let tel = Number(this.phone);
      let number = Number(this.number);


      axios
        .post("/api/order/checkout", {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          password: this.password,
          number: tel,
  
          street: this.street,
          numberStreet: this.number,
          zip: this.zip,
          state: this.state,
          reference: this.reference
        })
        .then((res) => {
          console.log(res);
          localStorage.clear();
        })
        .catch((err) => {
          console.log(err.response);
        });
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
          break;
        case "in":
          this.deliver = false;
          break;
        case "withdraw":
          this.deliver = false;
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
      this.productimg = this.product.productImage;
      console.log(this.product)
      axios
        .patch(`/api/products/edit/${this.product.id}`, {
          name: this.nameeditproduct,
          description: this.descriptionproduc,
          productImage: this.productimg,
          price: this.priceedit,
          stock: this.stockedit,
        })
        .then((response) => {
          setTimeout(() => {
            window.location.reload();
          }, 500);
        })
        .catch((err) => console.log(err.response));
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
          idCategory: this.idcategory,
          name: this.nameproducts,
          description: this.descriptionproducts,
          productImage: this.products_img,
          price: this.priceproduct,
          stock: this.stockproduct,
        })
        .then((response) => console.log(response))
        .catch((err) => {
          console.log(err.response.data);
          console.log(
            this.idcategory,
            this.nameproducts,
            this.descriptionproducts,
            this.products_img,
            this.priceproduct,
            this.stockproduct
          );
        });
    },
  },
  computed: {
    filterProducts() {
      return this.products.filter((product) =>
        product.name.toLowerCase().match(this.filterNameProduct.toLowerCase())
      );
    },
  },
});
app.mount("#app");
