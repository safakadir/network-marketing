import moment from 'moment';

const util = {}

util.clone = (obj) => {
    return Object.assign({}, obj);
}

util.now = () => {
    return new Date().getTime();
}

util.isNullOrUndef = (obj) => {
    /* JS Hint
     * 
     * Direct undefinity will yield error even while using this method. So avoid them in development.
     * 
     * >Direct undefinity: No correspondence
     *    A variable never declared or assigned. Never mentioned anywhere.
     *    Can't use if(variable) or isNullOrUndef(variable). Throws exception.
     *    These kind is problematic even if testing undefinity.
     *    Only works in typeof variable === 'undefined'; and without passing to function.
     * >Gained undefinity: Corresponds to undefined keyword
     *    An undefined field of a defined object or keyword undefined.
     *    You can use this kind of undefined value in if statements or functions.
     *    You can assign undefined to a variable and can test its undefinity.
     */
    return (typeof obj === 'undefined' || obj == null);
}

util.isEmpty = (obj) => {
    if(util.isNullOrUndef(obj)) return true; //TODO not sure about this
    if(typeof obj == 'string') return obj.trim() == '';
    else if(Array.isArray(obj)) return obj.length == 0;
    else if(typeof obj == 'object') return Object.keys(obj).length === 0 && obj.constructor === Object;
    else return false; //another type e.g. number is not empty.
    //else throw "Testing emptiness for an unexpected type "+(typeof obj);
}

util.getProperty = (obj, propName) => {
    if(util.isNullOrUndef(obj)) return undefined;
    if(util.isEmpty(propName)) return undefined;
    const indexOfDot = propName.indexOf('.');
    //log.debug("util.getProperty - keys: %s, propName: %s", keyListStr, propName);
    if(indexOfDot < 0) return obj[propName];
    else {
        const firstPropName = propName.substring(0,indexOfDot);
        var subObj = obj[firstPropName];
        if(typeof subObj === 'string') subObj = JSON.parse(subObj);
        else if(util.isNullOrUndef(subObj)) return undefined;
        return util.getProperty(subObj, propName.substring(indexOfDot+1));
    }
}

util.formatDate = (date) => {
    return moment(date).format('DD-MM-YYYY');
}

util.formatDateTime = (date) => {
    return moment(date).format('DD-MM-YYYY hh:mm');
}

util.capitalizeFirst = (str) => {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

util.snakeToCamel = (str) => {
    return str.replace(/([-_]\w)/g, g => g[1].toUpperCase());
} 

util.snakeToPascal = (str) => {
    return util.capitalizeFirst(util.snakeToCamel(str));
}

export default {
    install: function (Vue, options) {
        Vue.prototype.$gutil = util;
        Vue.gutil = util;
        Number.prototype.normalize = function() {
            return Math.round((this + Number.EPSILON) * 100) / 100;
        }
    }   
}

export const gutil = util;
