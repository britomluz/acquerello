const App = Vue.createApp({
  data() {
    return {
      firstname: {
        input: "",
        msg: "",
        correct: false,
      },
      email: {
        input: "",
        msg: "",
        correct: false,
      },
      lastname: {
        input: "",
        msg: "",
        correct: false,
      },
      password: {
        input: "",
        msg: "",
        correct: false,
      },
      phone: {
        input: "",
        msg: "",
        correct: false,
      },

      //
      
    };
  },
  created() {},
  methods: {
    validator_firstname(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (data.input.search(/^[a-zA-Z\s]*$/)) {
        data.msg = "field must be only letter";
        data.correct = false;
      } else if (data.input.length > 8) {
        data.msg = "firstname field should be less than 8 character";
        data.correct = false;
      } else if (data.input.length < 1) {
        data.msg = "firstname field should be more than 1 character";
        data.correct = false;
      } else {
        data.msg = "field is correct";
        data.correct = true;
      }
    },
    validator_lastname(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (data.input.search(/^[a-zA-Z\s]*$/)) {
        data.msg = "field must be only letter";
        data.correct = false;
      } else if (data.input.length > 8) {
        data.msg = "lastname field should be less than 8 character";
        data.correct = false;
      } else if (data.input.length < 1) {
        data.msg = "lastname field should be more than 1 character";
        data.correct = false;
      } else {
        data.msg = "field is correct";
        data.correct = true;
      }
    },
    validator_emial(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (
        data.input.search(
          /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/
        )
      ) {
        data.msg = "mail field is not formatted";
        data.correct = false;
      } else {
        data.msg = "mail field is correct";
        data.correct = true;
      }
    },
    validator_password(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (data.input.search(/[A-Za-z0-9!?-]{8,20}/)) {
        data.msg = "password must have between 8 and 20 characters";
        data.correct = false;
      } else {
        data.msg = "password field is correct";
        data.correct = true;
      }
    },
    validator_phone(data) {
      if (!data.input) {
        data.msg = "field is empty";
        data.correct = false;
      } else if (
        data.input.match(
          /^[+]?(1\|1\s|1|\d{3}\|\d{3}\s|)?((\(\d{3}\))|\d{3})(\|\s)?(\d{3})(\|\s)?(\d{4})$/g
        )
      ) {
        //does not accept format 0 but + is accepted just fill in field
        data.msg = "phone field is correct";
        data.correct = true;
      } else {
        data.msg = "only number";
        data.correct = false;
      }
    },
    send(e) {
      e.preventDefault()
      if (
        this.firstname.correct == true &&
        this.lastname.correct == true &&
        this.email.correct == true &&
        this.password.correct == true &&
        this.phone.correct == true
      ) {
      //here sweet alert pls because I don't know how to use it
      
      }
    },
  },
});

App.mount("#app");
