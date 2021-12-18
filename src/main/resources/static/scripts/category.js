const App = Vue.createApp({
    data() {
        return {
            user:[],
            categories: [],
            categoryName: "",
            categoryDescription: "",
            categoryImage: "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
            // categoryProducts: [],
            idCategory: 0,

            idEdited: 0,
            nameEdited: "",
            descriptionEdited: "",
            categoryImageEdited: "https://res.cloudinary.com/luz-brito/image/upload/v1638657510/Acquerello/imgDefault_qbhg4k.jpg",
            descriptionEdited:"",
            
            //upload category image
            //image products
            CLOUDINARY_URL: "",
            CLOUDINARY_UPLOAD_PRESET: "",
            urlImg: "",
            imagePreviewDos: "",
            imageUploaderDos: "",
        };
    },
    created() {
        this.load_user()
        axios.get('/api/categories')
            .then(response => {
                this.categories = response.data.categories
            })
            .catch(err => console.log(err))
    },
    methods: {
        load_user(){
            axios.get("/api/users/current")
            .then(res => {
              this.user = res.data
              this.card=res.data.card
              this.myaddress = res.data.address 
          })
            .catch(err =>{
              // err.message
              console.log(err.response)  
            } 
            )
        },
        createCategory(){
            axios.post('/api/categories/create',{
                // id: this.categoryId,
                name: this.categoryName,
                description: this.categoryDescription,
                categoryImage: this.categoryImage,
            })
            .then((res) => {
                console.log("Product created");
                swal({
                  title: "Good job!",
                  text: "Category added successfully",
                  icon: "success",
                  button: "OK!",
                })
                .then(window.location.reload())
            })
            .catch(err => console.log(err))
        },
        editCategory(){
            axios.patch(`/api/categories/edit/${this.idEdited}`,{
                name: this.nameEdited,
                description: this.descriptionEdited,
                categoryImage: this.categoryImageEdited,
            })
            .then((res) => {
                console.log("Product created");
                swal({
                  title: "Good job!",
                  text: "Category edited successfully",
                  icon: "success",
                  button: "OK!",
                })
                .then(window.location.reload())
            })
            .catch(err => console.log(err))
        },
        deleteCategory(){
            axios.delete(`/api/categories/delete/${this.idCategory}`)
                .then(() => swal('Category deleted'))
                .then()
                .then(res=>{
                    window.location.reload();
                })
                .catch(err => console.log(err))
        },
        uploadImageCategory(event) {
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
      
                this.categoryImage = this.imagePreviewDos.src
                this.categoryImageEdited = this.imagePreviewDos.src
      
      
                //console.log(this.products_img)
      
              })
      
          },
        logout(){
            axios.get("/api/logout")
            .then(res=>{
              console.log(res)
              window.location.href="/web/login.html"
            })
            .catch(err=>console.log(err))
          }
    },
});
App.mount("#app");
