import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListadoEmpleadoComponent } from './listado.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ListadoEmpleadoComponent', () => {
  let component: ListadoEmpleadoComponent;
  let fixture: ComponentFixture<ListadoEmpleadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoEmpleadoComponent, HttpClientTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(ListadoEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
