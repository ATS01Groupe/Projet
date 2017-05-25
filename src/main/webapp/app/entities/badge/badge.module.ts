import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    BadgeService,
    BadgePopupService,
    BadgeComponent,
    BadgeDetailComponent,
    BadgeDialogComponent,
    BadgePopupComponent,
    BadgeDeletePopupComponent,
    BadgeDeleteDialogComponent,
    badgeRoute,
    badgePopupRoute,
} from './';

const ENTITY_STATES = [
    ...badgeRoute,
    ...badgePopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BadgeComponent,
        BadgeDetailComponent,
        BadgeDialogComponent,
        BadgeDeleteDialogComponent,
        BadgePopupComponent,
        BadgeDeletePopupComponent,
    ],
    entryComponents: [
        BadgeComponent,
        BadgeDialogComponent,
        BadgePopupComponent,
        BadgeDeleteDialogComponent,
        BadgeDeletePopupComponent,
    ],
    providers: [
        BadgeService,
        BadgePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManBadgeModule {}
