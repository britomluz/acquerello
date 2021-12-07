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
      password_two:{
        input: "",
        msg: "",
        correct: false,
      },
      email_two:{
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
    validator_emial_2(data) {
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
    validator_password_2(data) {
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
    send() {
      if (
        this.firstname.correct == true &&
        this.lastname.correct == true &&
        this.email.correct == true &&
        this.password.correct == true &&
        this.phone.correct == true
      ) {
      axios.post("/api/users",{
        firstName:this.firstname.input,
        lastName:this.lastname.input,
        email:this.email.input,
        password:this.password.input,
        number:this.phone.input,
      })
      .then(response=>window.location.href="/web/myaccount.html")
      .catch(err=>{
        console.log(err.response.data)
        console.log(this.firstname.input,this.lastname.input,this.email.input,this.password.input,this.phone.input,)
      })
      }
    },
    login(){
        axios.post("/api/login",`email=${this.email_two.input}&password=${this.password_two.input}`)
        .then(response=>window.location.href="/web/myaccount.html")
        .catch(err=>{console.log(err.response.data)
        console.log(this.email_two.input,this.password_two.input)
      
      }) 
    }
  },
});

App.mount("#app");
