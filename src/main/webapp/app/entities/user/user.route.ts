import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { UserComponent } from './user.component';
import { UserDetailComponent } from './user-detail.component';
import { UserPopupComponent } from './user-dialog.component';
import { UserDeletePopupComponent } from './user-delete-dialog.component';

import { Principal } from '../../shared';

export const userRoute: Routes = [
    {
        path: 'user',
        component: UserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.user.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user/:id',
        component: UserDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.user.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userPopupRoute: Routes = [
    {
        path: 'user-new',
        component: UserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.user.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user/:id/edit',
        component: UserPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.user.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user/:id/delete',
        component: UserDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hotManApp.user.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
