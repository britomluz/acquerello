const App = Vue.createApp({
    data() {
        return {
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
            
        };
    },
    created() {
        axios.get('/api/categories')
            .then(response => {
                this.categories = response.data.categories
            })
            .catch(err => console.log(err))
    },
    methods: {
        createCategory(){
            axios.post('/api/categories/create',{
                // id: this.categoryId,
                name: this.categoryName,
                description: this.categoryDescription,
                categoryImage: this.categoryImage,
            })
                .then(() => swal('Category created'))
                .then(console.log("Category created"))
                .then(window.location.reload())
                .catch(err => console.log(err))
        },
        editCategory(){
            axios.patch(`/api/categories/edit/${this.idEdited}`,{
                name: this.nameEdited,
                description: this.descriptionEdited,
                categoryImage: this.categoryImageEdited,
            })
                .then(() => swal('Category edited'))
                .then(console.log("Category edited"))
                .then(window.location.reload())
                .catch(err => console.log(err))
        },
        deleteCategory(){
            axios.delete(`/api/categories/delete/${this.idCategory}`)
                .then(() => swal('Category deleted'))
                .then(console.log("Category deleted"))
                .then(res=>{
                    window.location.reload();
                })
                .catch(err => console.log(err))
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
