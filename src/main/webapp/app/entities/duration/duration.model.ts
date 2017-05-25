
const enum Typeduration {
    'JOUR',
    'SEMAINE',
    'MOIS',
    'ANNEE'

};
import { Client } from '../client';
export class Duration {
    constructor(
        public id?: number,
        public datecome?: any,
        public datego?: any,
        public duration?: number,
        public type?: Typeduration,
        public client?: Client,
    ) {
    }
}
