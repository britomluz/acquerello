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
      mainCourse:[],
      entriesSnacks:[],
      specials:[],
      chefPicks:[],
      soup:[],
      drinks:[],
      pasta:[],      
      vegetarian:[],
      salads:[],
    }
  },
  created() {
    //this.Admin_accept_order()
    this.loadProducts()
    this.loadCategories()

  },
  methods: {
    loadProducts() {
      axios.get('/api/products')
        .then(response => {         
          this.products = response.data.products
          //this.mainCourse = this.products.filter(product => product.categories)
        })
       .catch(err => console.log(err.response.data))
    },
    loadCategories(){
      axios.get('/api/categories')
      .then(response => {         
        this.categories = response.data.categories  
        console.log(this.categories)      
      })
     .catch(err => console.log(err.response.data))
    },
    createProduct() {
      axios.post('/api/products/create', idCategory = productId, {//ren, I added {} because I got an error 

        name: this.productName,
        description: this.description,
        productImage: this.productImage,
        price: this.price,
        stock: this.stock
      })
        .then(console.log("Product created"))
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
    }
  }
})
app.mount("#app");
