const App = Vue.createApp({
  data() {
    return {};
  },
  created() {},
  methods: {
    send_address() {
      axios
        .post()
        .then((response) => console.log(response))
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
