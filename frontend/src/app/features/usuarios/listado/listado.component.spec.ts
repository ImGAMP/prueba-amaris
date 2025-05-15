import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListadoComponent } from './listado.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ListadoComponent', () => {
  let component: ListadoComponent;
  let fixture: ComponentFixture<ListadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoComponent, HttpClientTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(ListadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
