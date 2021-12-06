const App = createApp({
  data() {
    return {};
  },
  created() {},
  methods: {
      Edit(){
          axios.patch("")
          .then(response=>console.log(response))
          .catch(err=>console.log(err))
      },
      Delete(){
        axios.delete("")
        .then(response=>console.log(response))
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
  },
});
App.mount("#app");
