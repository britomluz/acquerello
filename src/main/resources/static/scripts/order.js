const app = Vue.createApp({
  data() {
    return {
      products: [],
      productId: "",
      productName: "",
      productImage: "",
      price: "",
      stock: [],
      ordername: "",
      orderquantity: 0,
      priceorder: 0,
      totalorder: 0,
      categories:[],

      //product categories
      mainCourses:[],
      entriesSnacks:[],
      specials:[],
      chefPicks:[],
      soups:[],
      drinks:[],
      pastas:[],      
      vegetarians:[],
      salads:[],

      //cart
      qantity: "",
      totalQantity: "",
      product:[],      
      destacados:[],                
      cart:[],
      modal:[],
      search:'',
      quantity:[],
      searchProducts: false,

      //multistep form
      prevBtns: "",
            nextBtns: "",
            progress: "",
            formSteps: "",
            progressSteps: [],             
            progressActive:'',

      // save orders step by step

        firstName: "",
        lastName: "",
        phone: "",

      addessSelected: [],

        street: "",
        number: "",
        zip: "",
        reference: "",


        numberCard: "",
        cvv: "",
        vec: ""
    }
  },
  created() {
    //this.Admin_accept_order()
    this.loadProducts()
    this.loadCategories()

    this.showModal()
    if (localStorage.getItem('cart')) {
      this.cart = JSON.parse(localStorage.getItem('cart'))
      this.addToCart()
    }

  },
  methods: {
    loadProducts() {
      axios.get('/api/products')
        .then(response => {         
          this.products = response.data.products
          // this.products = localStorage.getItem("products")? JSON.parse(localStorage.getItem("products")) : [...response.data.products]
          console.log(this.products)
          //this.mainCourse = this.products.filter(product => product.categories)
        })
       .catch(err => console.log(err.response.data))
    },
    loadCategories(){
      axios.get('/api/categories')
      .then(response => {         
        this.categories = response.data.categories
        // this.categories = localStorage.getItem("categories")? JSON.parse(localStorage.getItem("categories")) : [...response.data.categories]
        this.entriesSnacks = [...this.categories.filter(categorie => categorie.name === "Entries & Snacks")[0].products]
        this.specials = [...this.categories.filter(categorie => categorie.name === "Specials")[0].products]
        this.chefPicks = [...this.categories.filter(categorie => categorie.name === "Chef Picks")[0].products]
        console.log(this.chefPicks)
        this.mainCourses = [...this.categories.filter(categorie => categorie.name === "Main Course")[0].products]
        this.soups = [...this.categories.filter(categorie => categorie.name === "Soup")[0].products]
        this.drinks = [...this.categories.filter(categorie => categorie.name === "Drinks")[0].products]
        this.pastas = [...this.categories.filter(categorie => categorie.name === "Pasta")[0].products]
        this.vegetarians = [...this.categories.filter(categorie => categorie.name === "Vegetarian")[0].products]
        this.salads = [...this.categories.filter(categorie => categorie.name === "Salads")[0].products]
        console.log(this.mainCourses)      
      })
     .catch(err => console.log(err))
    },
    createProduct() {
      axios.post('/api/products/create', idCategory = productId, {//ren, I added {} because I got an error 

        name: this.productName,
        description: this.description,
        productImage: this.productImage,
        price: this.price,
        stock: this.stock
      })
        .then(res => {
          console.log("Product created")
          swal({
            title: "Good job!",
            text: "You clicked the button!",
            icon: "success",
            button: "Aww yiss!",
          });
        })
        .catch(err => console.log(err))
    },
    deleteProduct() {
      axios.delete(`/api/products/delete/${this.id}`)
        .then(console.log("Product delete"))
        .catch(err => console.log(err))
    },
    editProduct() {
      axios.patch(`/api/products/edit/${this.id}`, {//ren, I added {} because I got an error 
        name: this.productName,
        description: this.description,
        productImage: this.productImage,
        price: this.price,
        stock: this.stock
      }
      )
        .then(console.log("Product Edit"))
        .catch(err => console.log(err))
    },

    Edit_order() {
      axios.patch(`/api/order/edit/${this.idOrder}`)
        .then(response => console.log(response))
        .catch(err => console.log(err))
    },
    Delete_order() {
      axios.delete(`/api/order/cancel/${this.idOrder}`)
        .then(response => console.log(response))
        .catch(err => console.log(err))
    },
    Admin_accept_order() {
      axios.get(`/api/order/confirm/${this.idOrder}`)
        .then(response => console.log(response))
        .catch(err => console.log(err))
    },
    user_Accept_order() {
      axios.post("/api/order/buy", {
        name: this.ordername,
        quantity: this.orderquantity,
        price: this.priceorder,
        total: this.totalorder
      })
        .then(response => console.log(response))
        .catch(err => console.log(err))
    },
    addToCart(id){       
      
      this.totalPrice = 0
      this.totalQantity = 0      

      for (let i = 0; i < this.products.length; i++){
          if(this.products[i].id == id){            
            // si no esta en el carrito lo agrega            
              if(!this.cart.includes(this.products[i])){

                  let obj={
                           quantity:1
                          }
                  let result = Object.assign(this.products[i],obj)
                  this.cart.push(this.products[i])

              } else {
                  // de lo contraria le suma +1 a la cantidad del producto 
                this.products[i].quantity++
              }
              this.totalPrice =  this.totalPrice + this.products[i].price
              this.totalQantity = this.totalQantity + 1
              }      
      }
      localStorage.setItem('cart', JSON.stringify(this.cart))

      },
      deleteOne(clickEvent){
        this.cart.forEach(product => {
            if(clickEvent.target.id == product.id){
                product.quantity --
                product.stock ++
            }
        })
        this.cart.forEach((product, i) =>{
        if (product.quantity == 0) {
          this.cart.splice(i, 1)
        }
      })
      localStorage.setItem('cart', JSON.stringify(this.cart))
        // localStorage.setItem("products", JSON.stringify(this.products))
      },
      addOne(clickEvent){ 
        this.cart.forEach(product => {  
          if(clickEvent.target.id == product.id)
            product.quantity ++
            product.stock -- 
        })

      console.log(this.cart);
    },
       
      showModal(id){
      let prod = this.products.filter(item => item.id == id)
      this.modal = prod                
      },
      calculateTotal(){
        let total = 0
        this.cart.forEach(product => {
          total += product.price * product.quantity
        })
        return total;
      },
      emptyCart(){
        this.cart.forEach(product => {
          // localStorage.removeItem("products")
          product.quantity = 0
        })
        location.reload()
      },
      //multistep form
      updateForms(btn, form1, form2, step){
        this.nextStep(btn, form1, form2);
        this.nextProgressBar(btn, step);
        
     },
    backForms(btn, form1, form2, step){
        this.prevStep(btn, form1, form2);
        this.prevProgressBar(btn, step);          
    },
    nextStep(btn, form1, form2){   
        //this.paymentsList()                 

        this.nextBtns = btn; //next1       
    
        this.formSteps1 = form1; //form1
        this.formSteps2 = form2; //form2   

        this.formSteps1.classList.remove("form-step-active");
        this.formSteps2.classList.add("form-step-active");
   },
    prevStep(btn, form1, form2){        
        this.prevBtns = btn; //next1        
        
        this.formSteps1 = form1; //form2
        this.formSteps2 = form2; //form1 

        this.formSteps1.classList.add("form-step-active");
        this.formSteps2.classList.remove("form-step-active");        
   },
    nextProgressBar(btn, step){        
        this.progressSteps1 = step       
        this.progressSteps1.classList.add("progress-step-active");               


        this.progress = this.$refs.progress                
        this.progressActive='progress-step-active'

        if(this.progressSteps1 == this.$refs.progressStep2){
            this.progress.style.width = "50%"; 
        } else if (this.progressSteps1 == this.$refs.progressStep3){
            this.progress.style.width = "100%";
        }
  },
    prevProgressBar(btn, step){        
        this.progressSteps1 = step       
        this.progressSteps1.classList.remove("progress-step-active");        

        this.progressActive='progress-step-active'
        
        if(this.progressSteps1 == this.$refs.progressStep2){
            this.progress.style.width = "0%"; 
        } else if (this.progressSteps1 == this.$refs.progressStep3){
            this.progress.style.width = "50%";
        }            

       // this.errorLoan = false
  }, 
  sendForm(e) {
    
  }

  },
  show_address(e) {
    if (e.target.value == "newAdress") {
      this.show = true;
    } else {
      this.show = false;
    }
  },
  send_address() {//zzzzz
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
})
app.mount("#app");
