export interface ITuit {
    id?: number;
    userEmisor?: string;
    userReceptor?: string;
    textoTuit?: string;
}

export class Tuit implements ITuit {
    constructor(public id?: number, public userEmisor?: string, public userReceptor?: string, public textoTuit?: string) {}
}
