import { useState } from "react";
import {
    Box,
    Card,
    CardContent,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow
} from "@mui/material"
import { useStock, useStockAccountFind, useStockPriceProfit } from "../hooks/queries";
import { useUserId } from "../hooks/lib";


export const PortfolioList: React.FC<{}> = () => {
    const [userId] = useUserId();
    const { data: stocks = [] } = useStock();
    const { data: accountStocks = [] } = useStockAccountFind({
        userId: userId as number,
    });

    const calculateTotalNetWorth = (): number => {
        let totalNetWorth = 0;
        accountStocks.forEach((accountStock) => {
            const currentStock = stocks.find(({ id }) => id === accountStock.stockId);
            if (!currentStock) return;
            totalNetWorth += (currentStock.currentStockPrice || 0) * (accountStock.amount || 0);
        });
        return totalNetWorth;
    }

    if (!accountStocks.length) {
        return (
            <Box
                sx={{
                    display: "flex",
                    alignItems: "center",
                    justifyContent: "center",
                    width: "100%",
                    mt: "100px",
                    mb: "100px"
                }}
            >
                <Typography variant="h5" component="div">
                    У вас пока нет акций
                </Typography>
            </Box>
        )
    }
    return (
        <Box sx={{ width: "100%", textAlign: "left", mt: "24px", mb: "24px" }}>
            <Typography sx={{ m: 1, ml: 4 }}>Портфель</Typography>
            <Card sx={{ m: 1, width: "45%" }}>
                <CardContent>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        НАИМЕНОВАНИЕ
                                    </Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        КОЛИЧЕСТВО
                                    </Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        ПРИБЫЛЬ/УБЫТКИ
                                    </Typography>
                                </TableCell>
                                <TableCell>
                                    <Typography sx={{ fontSize: "12px" }}>
                                        ТЕКУЩАЯ СТОИМОСТЬ
                                    </Typography>
                                </TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {accountStocks.map(({ stockId, amount }) => (
                                <PortfolioListItem
                                    key={stockId}
                                    stockId={stockId}
                                    amount={amount}
                                    stocks={stocks}
                                    userId={userId}
                                />
                            ))}
                        </TableBody>
                    </Table>
                    <Box style={{
                        display: "flex",
                        justifyContent: "space-between",
                        marginTop: "20px"
                    }}>
                        <Typography>
                            Oбщая стоимость:
                        </Typography>
                        <Typography sx={{ fontWeight: "600" }}>
                            {calculateTotalNetWorth()} <Typography sx={{ fontSize: "10px", display: "inline-block" }}>USD$</Typography>
                        </Typography>
                    </Box>
                </CardContent>
            </Card>
        </Box>
    )
}

const PortfolioListItem: React.FC<{
    stockId?: number,
    amount?: number,
    stocks: any[],
    userId?: number,
}> = ({ stockId, amount, stocks, userId }) => {
    const stock = stocks.find(({ id }) => id === stockId);
    const { data: profit } = useStockPriceProfit({ stockId, userId });
    return stock && (
        <TableRow>
            <TableCell>
                <Typography variant="h5">
                    {stock.sign}
                </Typography>
            </TableCell>
            <TableCell>
                <Typography>
                    {amount}
                </Typography>
            </TableCell>
            <TableCell>
                <Typography>
                    {profit} <Typography sx={{ fontSize: "10px", display: "inline-block" }}>USD$</Typography>
                </Typography>
            </TableCell>
            <TableCell>
                <Typography>
                    {(stock.currentStockPrice || 0) * (amount || 0)} <Typography sx={{ fontSize: "10px", display: "inline-block" }}>USD$</Typography>
                </Typography>
            </TableCell>
        </TableRow>
    )
}