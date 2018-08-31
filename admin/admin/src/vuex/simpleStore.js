var store = {
    debug: true,
    state: {
        sToken: ''
    },
    setTokenAction(newSToken) {
        if (this.debug) {
            console.log('setTokenAction triggered with', newSToken)
        }
        this.state.sToken = newSToken
    },
    clearTokenAction() {
        if (this.debug) {
            console.log('clearTokenAction triggered')
        }
        this.state.sToken = ''
    }
}

export default store;