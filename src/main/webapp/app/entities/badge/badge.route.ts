import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BadgeComponent } from './badge.component';
import { BadgeDetailComponent } from './badge-detail.component';
import { BadgePopupComponent } from './badge-dialog.component';
import { BadgeDeletePopupComponent } from './badge-delete-dialog.component';

import { Principal } from '../../shared';

export const badgeRoute: Routes = [
    {
        path: 'badge',
        component: BadgeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'badge/:id',
        component: BadgeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const badgePopupRoute: Routes = [
    {
        path: 'badge-new',
        component: BadgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'badge/:id/edit',
        component: BadgePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'badge/:id/delete',
        component: BadgeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.badge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
