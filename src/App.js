import './App.css';
import React, {useState} from "react";
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';

function App() {

    const initCalcForm = {
        loanAmount: '',
        termsInYears: '',
        annualInterestRate: '',
        loanType: ''
    };

    const [calculation, setCalculation] = useState(initCalcForm);

    const handleChange = (event) => {
        const {name, value} = event.target
        setCalculation({...calculation, [name]: value})
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        await fetch(`http://localhost:8089/api/payments`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(calculation)
        });
        setCalculation(initCalcForm);
    }

    const title = <h2>{'Calculate Loan'}</h2>;

    return (
        <div>
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="loanAmount">Loan Amount</Label>
                        <Input type="text" name="loanAmount" id="loanAmount"
                               value={calculation.loanAmount}
                               onChange={handleChange} autoComplete="loanAmount"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="termsInYears">Loan Terms</Label>
                        <Input type="text" name="termsInYears" id="termsInYears"
                               value={calculation.termsInYears}
                               onChange={handleChange} autoComplete="termsInYears"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="annualInterestRate">Annual Interest Rate</Label>
                        <Input type="text" name="annualInterestRate" id="annualInterestRate"
                               value={calculation.annualInterestRate}
                               onChange={handleChange} autoComplete="annualInterestRate"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="policyObjects">Loan Type</Label>
                        <Input type="text" name="loanType" id="loanType"
                               value={calculation.loanType}
                               onChange={handleChange} autoComplete="loanType"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Submit</Button>{' '}
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
}

export default App;
