import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HotManClientModule } from './client/client.module';
import { HotManDurationModule } from './duration/duration.module';
import { HotManRoomsModule } from './rooms/rooms.module';
import { HotManBadgeModule } from './badge/badge.module';
import { HotManGroupeModule } from './groupe/groupe.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HotManClientModule,
        HotManDurationModule,
        HotManRoomsModule,
        HotManBadgeModule,
        HotManGroupeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HotManEntityModule {}
