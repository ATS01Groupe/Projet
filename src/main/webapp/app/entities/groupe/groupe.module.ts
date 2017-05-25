import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    GroupeService,
    GroupePopupService,
    GroupeComponent,
    GroupeDetailComponent,
    GroupeDialogComponent,
    GroupePopupComponent,
    GroupeDeletePopupComponent,
    GroupeDeleteDialogComponent,
    groupeRoute,
    groupePopupRoute,
} from './';

const ENTITY_STATES = [
    ...groupeRoute,
    ...groupePopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GroupeComponent,
        GroupeDetailComponent,
        GroupeDialogComponent,
        GroupeDeleteDialogComponent,
        GroupePopupComponent,
        GroupeDeletePopupComponent,
    ],
    entryComponents: [
        GroupeComponent,
        GroupeDialogComponent,
        GroupePopupComponent,
        GroupeDeleteDialogComponent,
        GroupeDeletePopupComponent,
    ],
    providers: [
        GroupeService,
        GroupePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManGroupeModule {}
