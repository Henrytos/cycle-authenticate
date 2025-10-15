import { AfterViewInit, ChangeDetectionStrategy, Component, DEFAULT_CURRENCY_CODE, ElementRef, inject, OnInit, signal, ViewChild } from '@angular/core';
import { Title } from "../../components/title/title";
import { Header } from "../../components/header/header";
import { LucideAngularModule } from 'lucide-angular';
import { ListTransaction } from '../../components/list-transaction/list-transaction';
// IMPORTAÇÕES CORRETAS PARA O CHART.JS
import {
  Chart,
  ArcElement, // Elemento para o gráfico de pizza
  PieController, // Controlador para o gráfico de pizza
  Tooltip,
  Legend
} from 'chart.js';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CreateNewTransactionDialogForm } from '../../components/create-new-transaction-dialog-form/create-new-transaction-dialog-form';
import { GetMetricsDashboardService, GetMetricsDashboardServiceResponse } from '../../services/get-metrics-dashboard-service';
import { TransactionStateService } from '../../services/transaction-state-service';
import { CurrencyPipe, registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

export interface TransactionI {
  id: string
  senderId: string
  title: string
  value: number
  typeTransaction: "DEPOSIT" | "SPENT" | "INVESTMENT"
  dateOfPayment: string
  methodPayment: "PIX" | "TICKET" | "CREDIT"
}

registerLocaleData(localePt);

@Component({
  selector: 'app-dashboard',
  imports: [Title, Header, LucideAngularModule, ListTransaction, MatDialogModule, CurrencyPipe],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
  standalone: true,
  providers: [{ provide: DEFAULT_CURRENCY_CODE, useValue: 'BRL' }]
})
// Adiciona OnInit à implementação para consistência
export class Dashboard implements OnInit {

  spendingByCategory = [
    {
      id: 1,
      type: "Moradia",
      progress: 50,
      value: 2500
    },

    {
      id: 2,
      type: "Alimentação",
      progress: 40,
      value: 1200
    },
    {
      id: 3,
      type: "Saúde",
      progress: 30,
      value: 320
    }
  ];

  metrics = signal({} as GetMetricsDashboardServiceResponse)

  readonly dialog = inject(MatDialog);


  @ViewChild('chartCanvas') chartCanvas!: ElementRef;
  private chartInstance: Chart | undefined;
  constructor(
    private getMetricsDashboardService: GetMetricsDashboardService,
    private transactionStateService: TransactionStateService

  ) {
    transactionStateService.transactionMetricsUpdate$.subscribe((metrics) => {
      this.metrics.update(() => metrics)
      this.loadChart()
    })
  }

  loadChart() {
    if (this.chartInstance) {

      this.chartInstance.data.datasets[0].data[0] = this.metrics().deposit
      this.chartInstance.data.datasets[0].data[1] = this.metrics().spent
      this.chartInstance.data.datasets[0].data[2] = this.metrics().investment

      this.chartInstance.update()
    } else {
      this.load()
    }
  }

  ngOnInit(): void {

    this.getMetricsDashboardService.execute().subscribe((res) => {
      this.metrics.update(() => res);
      this.load()
    })
  }

  openDialog() {
    this.dialog.open(CreateNewTransactionDialogForm);
  }

  load(): void {

    // **REGISTRO DOS COMPONENTES NECESSÁRIOS PARA O GRÁFICO DE PIZZA**
    Chart.register(ArcElement, PieController, Tooltip, Legend);

    if (this.chartCanvas) {
      this.chartInstance = new Chart(this.chartCanvas.nativeElement, {
        type: 'doughnut',
        data: {
          labels: ['Ganhos', 'Gastos', 'Investimentos'],
          datasets: [{
            label: 'Vendas',
            data: [this.metrics().deposit, this.metrics().spent, this.metrics().investment],
            backgroundColor: [
              'rgba(85, 176, 46,0.8)',
              'rgba(233, 48, 48,0.8)',
              'rgba(255, 255, 255, 0.8)'
            ],
            borderColor: [
              'rgba(85, 176, 46,0.8)',
              'rgba(233, 48, 48,0.8)',
              'rgba(255, 255, 255, 0.8)'
            ],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false
        }
      });
    } else {
      console.error("Erro: O Canvas 'chartCanvas' não foi encontrado no template.");
    }
  }
}


