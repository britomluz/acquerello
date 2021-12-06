const app = Vue.createApp({
  data() {
    return {
      products: [],
      productId:"",
      productName="",
      productImage="",
      price: "",
      stock: [],

    }
  },
  created() {
    axios.get("/api/products")
    .then(response => {
      console.log(response)
      this.products = response.data
    })
    .catch(err=>console.log(err))
  },
  methods: {
      createProduct(){
        axios.post('/api/products/create', idCategory = productId, name = this.productName, description = this.description, productImage = this.productImage, price = this.price, stock = this.stock)
          .then(console.log("Product created"))
          .catch(err=>console.log(err))
      },
      deleteProduct(){
        axios.delete('/api/products/delete/'+id)
        .then(console.log("Product delete"))
        .catch(err=>console.log(err))
      },
      editProduct(){
        axios.post('/api/products/edit/'+id, 
        name = this.productName, 
        description = this.description, 
        productImage = this.productImage, 
        price = this.price,
        stock = this.stock
        )
        .then(console.log("Product Edit"))
        .catch(err=>console.log(err))
      }, 
      Admin_accept(){
          axios.get("")
          .then(response=>console.log(response))
          .catch(err=>console.log(err))
      },
      user_Accept(){
          axios.post()
          .then(response=>console.log(response))
          .catch(err=>console.log(err))
      }
  }
})
app.mount("#app");
