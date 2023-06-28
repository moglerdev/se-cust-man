import React from "react";
import { Line } from "react-chartjs-2";

const SalesChart: React.FC = () => {
  // Example data for the chart
  const chartData = {
    labels: ["January", "February", "March", "April", "May", "June"],
    datasets: [
      {
        label: "Sales",
        data: [100, 200, 150, 300, 250, 400],
        backgroundColor: "rgba(75, 192, 192, 0.2)",
        borderColor: "rgba(75, 192, 192, 1)",
        borderWidth: 1,
      },
    ],
  };

  return (
    <div className="sales-chart">
      <Line data={chartData} />
    </div>
  );
};

export default SalesChart;
