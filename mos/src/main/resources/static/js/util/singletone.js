// singleton.js

let instance = null;

function Singleton(data) {
    if (instance) {
        return instance;
    }

    this.data = data;
    instance = this;
}

Singleton.prototype.getData = function() {
    return this.data;
}

Singleton.prototype.setData = function(data) {
    this.data = data;
}

export default Singleton;
