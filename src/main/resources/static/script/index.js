const AppChat = {
    data() {
      return {
           nombre:{
             input:'',
             mensaje:'',
             clase:'',
             correcto:false
           },
           correo:{
             input:'',
             mensaje:'',
             clase:'',
             correcto:false
           },
           clave:{
            input:'',
            
           },
           password:"",
           addres:"",
           phone:0,
      }
    },
    created(){
                             
    },
    methods:{       
        
    },    
  }
  
  Vue.createApp(App).mount('#app')