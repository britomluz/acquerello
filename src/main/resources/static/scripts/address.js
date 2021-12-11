const App = Vue.createApp({
  data() {
    return {
      street: "",
      numberAddress: 0,
      zip: "",
      state: "",
      reference: "",
      addrefence: "",
      addstate: "",
      addzip: "",
      addnumber: 0,
      addstreet: "",
    };
  },
  created() {},
  methods: {
    send_address() {
      axios
        .post("/api/address/create", {
          street: this.street,
          number: this.numberAddress,
          zip: this.zip,
          state: this.state,
          reference: this.reference,
        })
        .then((response) => alert(response.data))
        .catch((err) => console.log(err.response.data));
    },
    edit_address() {
      axios
        .patch(`/api/address/edit?id=${1}`, {
          street: this.addstreet,
          number: this.addnumber,
          zip: this.addzip,
          state: this.addstate,
          reference: this.addrefence,
        })
        .then((response) => console.log(response))
        .catch((err) => console.log(err.response.data));
    },
    deLete_address() {
      axios
        .delete(`/api/address/delete?id=${2}`)
        .then((response) => console.log(response))
        .catch((err) => console.log(err.response.data));
    },
  },
});
let obv = App.mount("#app");
