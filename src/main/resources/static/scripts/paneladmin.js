const App = Vue.createApp({
  data() {
    return {
      products: [],
      orders: [],
      idproducts: [],
      name_products:"hola",
      description_products:"hola",
      productsimg:"https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
      priceproduct:0,
      stockproduct:0,
      idproduct:1
    };
  },
  created() {
    axios.get("/api/products").then((response) => {
      this.products = response.data.products;
      console.log(this.products);
    });
    this.loadcreate();
  },
  methods: {
    loadcreate() {
      axios
        .get(`/api/order`)
        .then((response) => {
          this.orders = response.data;
          console.log(this.orders);
        })
        .catch((err) => console.log(err));
    },
    product_add() {
      axios
        .post("/api/products/create", {
          idCategory:this.idproduct,
          name: this.name_products,
          description: this.description_products,
          productImage:this.productimg,
          price: this.priceproduct,
          stock: this.stockproduct,
        })
        .then(response=>console.log(response))
        .catch(err=>{
          console.log(this.idproduct,this.name_products,this.description_products,this.productsimg,this.priceproduct,this.stockproduct)
          console.log(err.response.data)
        })
    },
    deleteProduct() {
    
      axios
        .delete(`/api/products/delete/${this.idproducts}`)
        .then((response) => console.log(response.data))
        .catch((err) => console.log(err));
    },
  },
});
App.mount("#app");
