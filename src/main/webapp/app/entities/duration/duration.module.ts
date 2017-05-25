import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HotManSharedModule } from '../../shared';
import {
    DurationService,
    DurationPopupService,
    DurationComponent,
    DurationDetailComponent,
    DurationDialogComponent,
    DurationPopupComponent,
    DurationDeletePopupComponent,
    DurationDeleteDialogComponent,
    durationRoute,
    durationPopupRoute,
    DurationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...durationRoute,
    ...durationPopupRoute,
];

@NgModule({
    imports: [
        HotManSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DurationComponent,
        DurationDetailComponent,
        DurationDialogComponent,
        DurationDeleteDialogComponent,
        DurationPopupComponent,
        DurationDeletePopupComponent,
    ],
    entryComponents: [
        DurationComponent,
        DurationDialogComponent,
        DurationPopupComponent,
        DurationDeleteDialogComponent,
        DurationDeletePopupComponent,
    ],
    providers: [
        DurationService,
        DurationPopupService,
        DurationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManDurationModule {}
