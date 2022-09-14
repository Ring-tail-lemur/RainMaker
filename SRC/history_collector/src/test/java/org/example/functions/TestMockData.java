package org.example.functions;

public class TestMockData {
	public static String test1Graphql = "{repository(owner: \"{{owner}}\", name: \"{{repo}}\") {pullRequest(number: 1) {commits(first: 10) {edges {node {commit {oid message}}}}comments(first: 10) {edges {node {body author {login}}}}reviews(first: 10) {edges {node {state}}}}}}";
	public static String test1BindParameterGraphql = "{repository(owner: \"Ring-tail-lemur\", name: \"RainMaker\") {pullRequest(number: 1) {commits(first: 10) {edges {node {commit {oid message}}}}comments(first: 10) {edges {node {body author {login}}}}reviews(first: 10) {edges {node {state}}}}}}";
	public static String test1GraphqlConfig = "{test1: {graphql: test1.graphql, mapping:{} }}";
	public static String test1Element = "{graphql: test1.graphql, mapping:{} }";
}
