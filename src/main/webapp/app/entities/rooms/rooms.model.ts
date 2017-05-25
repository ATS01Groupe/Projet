
const enum Typedroom {
    'VIP',
    'SIMPLE',
    'MOYEN'

};

const enum Etatromms {
    'DISPONIPLE',
    'OCCUPE',
    'ENTRETIEN'

};
import { Badge } from '../badge';
import { Client } from '../client';
export class Rooms {
    constructor(
        public id?: number,
        public romsnumber?: string,
        public typeroom?: Typedroom,
        public etat?: Etatromms,
        public badge?: Badge,
        public client?: Client,
    ) {
    }
}
