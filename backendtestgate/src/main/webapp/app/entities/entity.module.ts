import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'position',
        loadChildren: () => import('./backendtest/position/position.module').then(m => m.BackendtestPositionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BackendtestgateEntityModule {}
