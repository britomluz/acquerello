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
      orderDetails:"",

      //each order
      order: "",
      //users
      users:[],
      user:[],
    };
  },
  created() {
    axios.get("/api/products").then((response) => {
      this.products = response.data.products;
    });
    this.loadcreate();
    this.loadDataOrders()
    this.loadDataOrderDetails()
    this.loadDataUser()
    this.loadUsers()

  },
  methods: {
    loadUsers(){
      axios.get(`/api/users`)
      .then((response) => {
        this.users = response.data;
        //console.log(this.orders)
      })
      .catch((err) => console.log(err));
    },
    loadDataUser(){
      const urlParam = new URLSearchParams(window.location.search);
      const id = urlParam.get('id');

      axios.get(`/api/user/${id}`)
        .then(res => {
          this.user = res.data
          //.sort((a,b) => parseInt(b.id - a.id))

        })
        .catch(err => err.message)
    },
    showUser(e) {
      let id = e.target.parentElement.id
      window.location.href = `./client-details.html?id=${id}`
    },    
    showOrder(e) {

      let id = e.target.parentElement.id
      window.location.href = `./order-details.html?id=${id}`

    },    
    loadDataOrders() {
      const urlParam = new URLSearchParams(window.location.search);
      const id = urlParam.get('id');

      axios.get(`/api/order/${id}`)
        .then(res => {
          this.order = res.data
          //.sort((a,b) => parseInt(b.id - a.id))


        })
        .catch(err => err.message)
    },
    showOrderDetails(e) {
      let id = e.target.parentElement.id
      window.location.href = `./order-details.html?id=${id}`

    },
    loadDataOrdersDetails() {
      const urlParam = new URLSearchParams(window.location.search);
      const id = urlParam.get('id');

      axios.get(`/api/order/${id}`)
        .then(res => {
          this.order = res.data
          //.sort((a,b) => parseInt(b.id - a.id))


        })
        .catch(err => err.message)
    },
    loadcreate() {
      axios.get(`/api/order`)
        .then((response) => {
          this.orders = response.data;
          //console.log(this.orders)
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
      axios.post("/api/products/create", {
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
      axios.delete(`/api/products/delete/${this.idproducts}`)
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
    editOrderState(e){      
      console.log(e.target.firstChild)
      
      let orderId = e.target.firstChild.id
      let orderState = e.target.firstChild.value
      console.log(orderId)
      console.log(orderState)

      axios.patch('/api/admin/order/edit',`id=${orderId}&type=${orderState}`)
      .then(res => {
        console.log(res)
        window.location.reload();
      }).catch(err => {
        console.log(err.data)
      })
    },
    loadDataOrderDetails(){
      axios.get("/api/order/details")
           .then((response) => {           
        this.orderDetails = response.data;
        

        this.orderDetails = this.orderDetails.filter(orderDetails => orderDetails.orderId === this.order.id)
        console.log(this.orderDetails)
        //console.log(this.order.id)   
      
      });
    },
    downloadInvoice(e){            
      this.idTransfer = e.target.firstChild.value 
      let idOrder = this.order.id
      axios.post("/api/bill/pdf",`id=${this.order.id}`,{ responseType: 'blob' }, {headers:{"Content-type": "application/pdf" }})
         .then((res) => {
           let disposition = res.headers['content-disposition']
           let filename = decodeURI(disposition.substring(21))
           let blob = new Blob([res.data], {type: 'application/pdf'});
           let objectUrl = URL.createObjectURL(blob);
           let link = document.createElement("a");
           //let filename = "transaccion"+ this.idTransfer;  

           link.href = objectUrl;
           link.setAttribute("download", filename);
           document.body.appendChild(link);
           link.click();
         })
         .catch(err=> {
           console.log("No se puede descargar el pdf");
           });
   },
  },
});
App.mount("#app");
