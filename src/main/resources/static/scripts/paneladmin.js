const App = Vue.createApp({
  data() {
    return {
      products: [],
      orders: [],
      idproducts: 0,
      nameproducts:"",
      descriptionproducts:"",
      productsimg:"https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
      priceproduct:0,
      stockproduct:0,
      idcategory:1,
      idpatch:0,
      namepatch:"",
      descriptionpatch:"",
      imgpatch:"https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
      pricepatch:0,
      stockpatch:0
    };
  },
  created() {
    axios.get("/api/products").then((response) => {
      this.products = response.data.products;
    });
    this.loadcreate();
  },
  methods: {
    loadcreate() {
      axios
        .get(`/api/order`)
        .then((response) => {
          this.orders = response.data;
        })
        .catch((err) => console.log(err));
    },
    // previewfile(e){
    //   console.log(e.target.files[0].name)
    //   this.productsimg = e.target.files[0]
    //   console.log(this.productimg)
    // },
    product_add() {
      // const fd = new FormData;
      // fd.append('image',this.productsimg,this.productimg.name)
      axios
        .post("/api/products/create", {
          idCategory:this.idcategory,
          name: this.nameproducts,
          description:this.descriptionproducts,
          productImage:this.productsimg,
          price: this.priceproduct,
          stock:this.stockproduct,
        })
        .then(response=>console.log(response))
        .catch(err=>{
          console.log(err.response.data)
          console.log(this.idcategory,this.nameproducts,this.descriptionproducts,this.productsimg,this.priceproduct,this.stockproduct)
        })
    },
    deleteProduct() {
      axios
        .delete(`/api/products/delete/${this.idproducts}`)
        .then((response) => console.log(response.data))
        .catch((err) => console.log(err));
    },
    path_product(){
      console.log(this.idpatch,this.namepatch,this.descriptionpatch,this.imgpatch,this.pricepatch,this.stockpatch)
      axios.patch(`/api/products/edit/${this.idpatch}`,{
        name:this.namepatch,
        description:this.descriptionpatch,
        productImage:this.imgpatch,
        price:this.pricepatch,
        stock:this.stockpatch,
      })
      .then(response=>console.log(response))
      .catch(err=>console.log(err))
    }
  },
});
App.mount("#app");
