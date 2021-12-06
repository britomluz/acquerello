const app = Vue.createApp({
  data() {
    return {
      products: [],
      productId:"",
      productName:"",
      productImage:"",
      price: "",
      stock: [],
      ordername:"",
      orderquantity:0,
      priceorder:0,
      totalorder:0
    }
  },
  created() {
    axios.get("/api/products")
    .then(response => {
      console.log(response)
      this.products = response.data
    })
    .catch(err=>console.log(err))
    this.Admin_accept_order();
  },
  methods: {
      createProduct(){
        axios.post('/api/products/create', idCategory = productId,{//ren, I added {} because I got an error 

          name : this.productName,
          description : this.description, 
          productImage : this.productImage,
          price : this.price,
          stock : this.stock
        })
          .then(console.log("Product created"))
          .catch(err=>console.log(err))
      },
      deleteProduct(){
        axios.delete(`/api/products/delete/${this.id}`)
        .then(console.log("Product delete"))
        .catch(err=>console.log(err))
      },
      editProduct(){
        axios.patch(`/api/products/edit/${this.id}`, {//ren, I added {} because I got an error 
          name : this.productName, 
          description : this.description, 
          productImage : this.productImage, 
          price : this.price,
          stock : this.stock
        }
        )
        .then(console.log("Product Edit"))
        .catch(err=>console.log(err))
      }, 

    Edit_order(){
        axios.patch(`/api/order/edit/${this.idOrder}`)
        .then(response=>console.log(response))
        .catch(err=>console.log(err))
    },
    Delete_order(){
      axios.delete(`/api/order/cancel/${this.idOrder}`)
      .then(response=>console.log(response))
      .catch(err=>console.log(err))
    },
    Admin_accept_order(){
      axios.get(`/api/order/confirm/${this.idOrder}`)
      .then(response=>console.log(response))
        .catch(err=>console.log(err))
    },
    user_Accept_order(){
        axios.post("/api/order/buy",{
          name: this.ordername,
          quantity: this.orderquantity,
          price:this.priceorder,
          total: this.totalorder
        })
        .then(response=>console.log(response))
        .catch(err=>console.log(err))
    }
  }
})
app.mount("#app");
