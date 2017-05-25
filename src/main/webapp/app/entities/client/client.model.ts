import { Rooms } from '../rooms';
import { Groupe } from '../groupe';
import { Duration } from '../duration';
export class Client {
    constructor(
        public id?: number,
        public fistname?: string,
        public lastname?: string,
        public cartnumenber?: string,
        public telephone?: string,
        public email?: string,
        public room?: Rooms,
        public groupe?: Groupe,
        public durre?: Duration,
    ) {
    }
}
