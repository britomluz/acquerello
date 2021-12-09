const App = Vue.createApp({
  data() {
    return {
      products: [],
      orders: [],
      idproducts: 0,
      nameproducts: "",
      descriptionproducts: "",
      productsimg: "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
      priceproduct: 0,
      stockproduct: 0,
      idcategory: 1,
      idpatch: 0,
      namepatch: "",
      descriptionpatch: "",
      imgpatch: "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
      pricepatch: 0,
      stockpatch: 0,
      idOrder: "",
      orderState: "",
      orderStates: ["PENDING", "IN_PROCESS", "DELIVERED", "CANCELED"],
      orderStateSelected: "",
      idOrderDelete: "",

      //each order
      order: ""
    };
  },
  created() {
    axios.get("/api/products").then((response) => {
      this.products = response.data.products;
    });
    this.loadcreate();
    this.loadDataOrders()
  },
  methods: {
    showOrder(e){
      let id = e.target.id
      window.location.href = `./orders.html?id=${id}`
       console.log(id)
  },
  loadDataOrders(){
      const urlParam = new URLSearchParams(window.location.search);
      const id = urlParam.get('id');
      
      axios.get(`/api/clients/current/cards/${id}`)
            .then(res => {                
              this.order = res.data.sort((a,b) => parseInt(b.id - a.id))
              console.log(this.order)
              
          })
            .catch(err => err.message)
      },
    loadcreate() {
      axios
        .get(`/api/order`)
        .then((response) => {
          this.orders = response.data;
          console.log(this.orders)
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
          idCategory: this.idcategory,
          name: this.nameproducts,
          description: this.descriptionproducts,
          productImage: this.productsimg,
          price: this.priceproduct,
          stock: this.stockproduct,
        })
        .then(response => console.log(response))
        .catch(err => {
          console.log(err.response.data)
          console.log(this.idcategory, this.nameproducts, this.descriptionproducts, this.productsimg, this.priceproduct, this.stockproduct)
        })
    },
    deleteProduct() {
      axios
        .delete(`/api/products/delete/${this.idproducts}`)
        .then((response) => console.log(response.data))
        .catch((err) => console.log(err));
    },
    path_product() {
      console.log(this.idpatch, this.namepatch, this.descriptionpatch, this.imgpatch, this.pricepatch, this.stockpatch)
      axios.patch(`/api/products/edit/${this.idpatch}`, {
        name: this.namepatch,
        description: this.descriptionpatch,
        productImage: this.imgpatch,
        price: this.pricepatch,
        stock: this.stockpatch,
      })
        .then(response => console.log(response))
        .catch(err => console.log(err))
    },

    // Brian Section

    getOrderState() {
      let order = this.orders.filter(o => o.id == this.idOrder);

      this.orderState = order[0].orderState;

      console.log(this.orderState)
    },
    changeOrderState() {
      console.log(this.orderStateSelected)
      axios.patch(`/api/order/edit/${this.idOrder}?orderState=${this.orderStateSelected}`).then(res => {
        const div = document.getElementById("response");
        div.innerText = res.data
      }).catch(err => {
        const div = document.getElementById("response");
        div.innerText = err.response.data
      })
    },
    deleteOrder() {
      axios.delete(`/api/order/cancel/${this.idOrderDelete}`).then(res => {
        const div = document.getElementById("response");
        div.innerText = res.data
      }).catch(err => {
        const div = document.getElementById("response");
        div.innerText = err.response.data
      })
    },
  },
});
App.mount("#app");
