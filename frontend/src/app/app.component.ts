import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <div class="app">
      <h1>RevCart - Grocery Delivery</h1>
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [`
    .app {
      font-family: Arial, sans-serif;
      padding: 20px;
    }
  `]
})
export class AppComponent {
  title = 'RevCart';
}