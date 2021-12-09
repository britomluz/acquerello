const App = Vue.createApp({
    data() {
        return {
            card: [],
            amount: "",
        };
    },
    created() {
        axios.get('/api/cards')
            .then(response => {
                console.log(response.data)
                this.card = response.data
            })
            .catch(err => console.log(err))
    },
    methods: {
        createCard() {
            axios.post('/api/cards/create')
                .then(console.log("Card create"))
                .catch(err => console.log(err))
        },
        deleteCard() {
            axios.patch(`/api/cards/delete${this.id}`)
                .then(console.log("Card delete"))
                .catch(err => console.log(err))
        },
        addBalance() {
            axios.patch(`/api/cards/add-balance/${this.id}?amount=${this.amount}`,)
                .then(console.log("Balance Add"))
                .catch(err => console.log(err))
        }
    },
});
App.mount("#app");
