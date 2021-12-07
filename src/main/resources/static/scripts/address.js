const App = Vue.createApp({
  data() {
    return {
      street:"",
      numbetAddres:0,
      zip:"",
      state:"",
      reference:"",
    };
  },
  created() {},
  methods: {
    send_address(e) {
      e.preventDefault()
      axios
        .post("/address/create", {
          street: this.street,
          number: this.numbetAddres,
          zip: this.zip,
          state: this.state,
          reference:this.reference,
        })
        .then((response) => alert(response.data))
        .catch((err) => console.log(err));
    },
    edit_address() {
      axios
        .patch()
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
    deLete_address() {
      axios
        .delete()
        .then((response) => console.log(response))
        .catch((err) => console.log(err));
    },
  },
});
App.mount("#app");
