import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BackendtestgateSharedModule } from 'app/shared/shared.module';
import { BackendtestgateCoreModule } from 'app/core/core.module';
import { BackendtestgateAppRoutingModule } from './app-routing.module';
import { BackendtestgateHomeModule } from './home/home.module';
import { BackendtestgateEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    BackendtestgateSharedModule,
    BackendtestgateCoreModule,
    BackendtestgateHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BackendtestgateEntityModule,
    BackendtestgateAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class BackendtestgateAppModule {}
