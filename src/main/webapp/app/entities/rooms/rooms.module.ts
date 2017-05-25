import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    RoomsService,
    RoomsPopupService,
    RoomsComponent,
    RoomsDetailComponent,
    RoomsDialogComponent,
    RoomsPopupComponent,
    RoomsDeletePopupComponent,
    RoomsDeleteDialogComponent,
    roomsRoute,
    roomsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...roomsRoute,
    ...roomsPopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RoomsComponent,
        RoomsDetailComponent,
        RoomsDialogComponent,
        RoomsDeleteDialogComponent,
        RoomsPopupComponent,
        RoomsDeletePopupComponent,
    ],
    entryComponents: [
        RoomsComponent,
        RoomsDialogComponent,
        RoomsPopupComponent,
        RoomsDeleteDialogComponent,
        RoomsDeletePopupComponent,
    ],
    providers: [
        RoomsService,
        RoomsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManRoomsModule {}
