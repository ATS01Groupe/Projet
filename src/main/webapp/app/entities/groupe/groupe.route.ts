import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GroupeComponent } from './groupe.component';
import { GroupeDetailComponent } from './groupe-detail.component';
import { GroupePopupComponent } from './groupe-dialog.component';
import { GroupeDeletePopupComponent } from './groupe-delete-dialog.component';

import { Principal } from '../../shared';

export const groupeRoute: Routes = [
    {
        path: 'groupe',
        component: GroupeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.groupe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'groupe/:id',
        component: GroupeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.groupe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const groupePopupRoute: Routes = [
    {
        path: 'groupe-new',
        component: GroupePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.groupe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'groupe/:id/edit',
        component: GroupePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.groupe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'groupe/:id/delete',
        component: GroupeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.groupe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
